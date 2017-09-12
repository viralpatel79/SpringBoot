package com.textbookvalet.rest.v1.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.social.RevokedAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.textbookvalet.commons.AuthProviderEnum;
import com.textbookvalet.commons.School;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.filters.TokenHandler;
import com.textbookvalet.rest.v1.dto.AuthDTO;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.utils.PasswordUtil;
import com.textbookvalet.ui.www.security.AuthenticationManagerExtended;

@Controller
@RequestMapping("/auth")
public class AuthRestController {

	private final Logger log = LoggerFactory.getLogger(AuthRestController.class);

	public static final String FB_PROFILE_PIC_LINK = "http://graph.facebook.com/%s/picture";

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private AuthenticationManagerExtended authManager;

	@Autowired
	private TokenHandler tokenHandler;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody AuthDTO emailLogin(@RequestParam(name = "user[email]") String email,
			@RequestParam(name = "user[password]") String password, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AuthDTO authDTO = new AuthDTO();

		authManager.setRequest(request);
		Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

		auth = authManager.authenticate(auth);

		User user = (User) BeanUtils.cloneBean((User) auth.getPrincipal());

		user.setPassword(null);
		user.setEncryptedPassword(null);

		authDTO.setProfile(user);

		if (user.getSchoolId() > 0) {
			School school = schoolService.getById(user.getSchoolId());
			user.setSchool(school);
		}

		String token = tokenHandler.createTokenForUser(user.getId());

		log.debug("Token generated for the user: " + email);

		authDTO.setToken(token);

		return authDTO;
	}

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public @ResponseBody AuthDTO facebook(@RequestParam(required = true) String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AuthDTO authDTO = new AuthDTO();

		Facebook facebook = new FacebookTemplate(code);

		try {
			org.springframework.social.facebook.api.User userProfile = facebook.userOperations().getUserProfile();

			if (userProfile == null) {
				throw new UserException("Invalid token provided");
			}

			if (StringUtils.isBlank(userProfile.getEmail())) {
				throw new UserException("You must authorize email in Facebook in order to login.");
			}

			String authUid = userProfile.getId();

			if (StringUtils.isBlank(authUid)) {
				throw new UserException("Invalid token provided");
			}

			User user = userService.findByAuthProviderAndUID(AuthProviderEnum.FACEBOOK.getValue(), authUid);

			if (user == null) {
				user = userService.findByEmail(userProfile.getEmail());
			}

			User newUser = user;
			if (user == null) {// go ahead and create new user
				newUser = new User();

				newUser.setPhone("0000000000");
				newUser.setSchoolId(schoolService.getAll().get(0).getId());
				newUser.setEncryptedPassword(PasswordUtil.encryptPassword(PasswordUtil.generatePassword()));
			}

			newUser.setAuthProvider(AuthProviderEnum.FACEBOOK.getValue());
			newUser.setAuthUid(authUid);
			newUser.setFacebookProfilePicture(String.format(FB_PROFILE_PIC_LINK, authUid));
			newUser.setFullName(userProfile.getName());
			newUser.setFirstName(userProfile.getFirstName());
			newUser.setLastName(userProfile.getLastName());
			newUser.setEmail(userProfile.getEmail());

			user = userService.save(newUser);
			user.setSchool(schoolService.getById(user.getSchoolId()));

			String token = tokenHandler.createTokenForUser(user.getId());

			log.debug("Token generated for the user: " + user.getId());

			authDTO.setToken(token);
			authDTO.setProfile(user);

			return authDTO;
		} catch (RevokedAuthorizationException e) {
			throw new UserException("Please provide a valid token. This token is not longer valid.");
		}
	}

	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public @ResponseBody AuthDTO facebookOAuth(@RequestParam(required = true) String access_token,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return facebook(access_token, request, response);
	}

}