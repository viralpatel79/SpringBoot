package com.textbookvalet.ui.www.security;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.SessionVariables;
import com.textbookvalet.commons.User;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.utils.PasswordUtil;

@Service("AuthenticationManagerExtended")
@Scope("prototype")
public class AuthenticationManagerExtended implements AuthenticationManager {

	private final Logger log = LoggerFactory.getLogger(AuthenticationManagerExtended.class);

	@Autowired
	UserService userService;

	public static final String INVALID_USERNAME_PASSWORD = "Invalid Username or Password.";
	public static final String USERNAME_FIELD_EMPTY = "Username field can not be empty.";
	public static final String PASSWORD_FIELD_EMPTY = "Password field can not be empty.";

	private HttpServletRequest request;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {

			Date startTime = new Date();

			if (StringUtils.isBlank((String) authentication.getPrincipal())) {
				throw new BadCredentialsException(INVALID_USERNAME_PASSWORD);
			}

			String username = (String) authentication.getPrincipal();
			String password = (String) authentication.getCredentials();

			log.info("Authenticating User with username: " + username);

			Authentication auth = varifyAndAuthenticate(username, password, request);

			Date endTime = new Date();

			log.debug(
					"Total Authenticatoin time: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " seconds..");

			return auth;

		} catch (Exception e) {
			log.info("Authentication Failed: " + e.getMessage());
			throw e;
		}
	}

	public Authentication varifyAndAuthenticate(String username, String password, HttpServletRequest request) {

		if (StringUtils.isBlank(username)) {
			throw new BadCredentialsException(USERNAME_FIELD_EMPTY);
		}

		if (StringUtils.isBlank(password)) {
			throw new BadCredentialsException(PASSWORD_FIELD_EMPTY);
		}

		User user = userService.findByEmail(username);

		if (user == null) {
			throw new BadCredentialsException(INVALID_USERNAME_PASSWORD);
		}

		if (!PasswordUtil.matchPassword(password, user.getEncryptedPassword())) {
			throw new BadCredentialsException(INVALID_USERNAME_PASSWORD);
		}

		user.setPassword(password);

		List<GrantedAuthority> grantedAuthorities = new LinkedList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CLIENT");
		grantedAuthorities.add(authority);

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SessionVariables.USER, user);
		sessionMap.put(SessionVariables.POSER_USER, user);

		return new CustomUsernamePasswordAuthenticationToken(user, sessionMap, grantedAuthorities);
	}

}