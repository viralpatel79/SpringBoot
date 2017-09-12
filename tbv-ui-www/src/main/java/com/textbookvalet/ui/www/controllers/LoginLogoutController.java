package com.textbookvalet.ui.www.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.textbookvalet.commons.AccessToken;
import com.textbookvalet.commons.AuthProviderEnum;
import com.textbookvalet.commons.SessionVariables;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.v1.controllers.AuthRestController;
import com.textbookvalet.services.AccessTokensService;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.utils.PasswordUtil;
import com.textbookvalet.ui.www.security.AuthenticationManagerExtended;
import com.textbookvalet.ui.www.security.CustomUsernamePasswordAuthenticationToken;

@Controller
public class LoginLogoutController extends BaseController {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(LoginLogoutController.class);

	@Autowired
	AuthenticationManagerExtended authManager;

	@Autowired
	UserService userService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	AccessTokensService accessTokensService;

	private Facebook facebook;
	private ConnectionRepository connectionRepository;

	public LoginLogoutController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/doLogin")
	public ModelAndView doLogin(@ModelAttribute User user, HttpServletRequest request) {

		authManager.setRequest(request);
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		auth = authManager.authenticate(auth);

		SecurityContext securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);

		return new ModelAndView("home");
	}

	@RequestMapping("/facebookAuth")
	public String facebookUser(ModelMap model) {

		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}

		final String accessToken = connectionRepository.findPrimaryConnection(Facebook.class).createData()
				.getAccessToken();

		System.out.println("facebook access token: " + accessToken);

		model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());

		org.springframework.social.facebook.api.User userProfile = facebook.userOperations().getUserProfile();

		if (userProfile == null) {
			throw new UserException("Invalid facebook authentication.");
		}

		if (StringUtils.isBlank(userProfile.getEmail())) {
			throw new UserException("You must provide an email access from Facebook in order to authenticate");
		}

		User user = userService.findByEmail(userProfile.getEmail());

		User newUser = user;
		if (user == null) {// go ahead and create new user
			newUser = new User();

			newUser.setPhone("0000000000");
			newUser.setSchoolId(schoolService.getAll().get(0).getId());
			newUser.setEncryptedPassword(PasswordUtil.encryptPassword(PasswordUtil.generatePassword()));
		}

		newUser.setAuthProvider(AuthProviderEnum.FACEBOOK.getValue());
		newUser.setAuthUid(userProfile.getId());
		newUser.setFacebookProfilePicture(String.format(AuthRestController.FB_PROFILE_PIC_LINK, userProfile.getId()));
		newUser.setFullName(userProfile.getName());
		newUser.setFirstName(userProfile.getFirstName());
		newUser.setLastName(userProfile.getLastName());
		newUser.setEmail(userProfile.getEmail());

		user = userService.save(newUser);

		AccessToken token = accessTokensService.findByUserId(user.getId());

		if (token != null) {
			if (!token.getToken().equals(accessToken)) {
				accessTokensService.updateTokenByUserId(accessToken, token.getId());
			}
		} else {

			AccessToken newToken = new AccessToken();
			newToken.setUserId(user.getId());
			newToken.setToken(accessToken);
			newToken.setCreatedAt(new Timestamp(new Date().getTime()));

			accessTokensService.save(newToken);
		}

		List<GrantedAuthority> grantedAuthorities = new LinkedList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CLIENT");
		grantedAuthorities.add(authority);

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(SessionVariables.USER, user);
		sessionMap.put(SessionVariables.POSER_USER, user);

		Authentication auth = new CustomUsernamePasswordAuthenticationToken(user, sessionMap, grantedAuthorities);

		SecurityContext securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);

		PagedList<Post> feed = facebook.feedOperations().getFeed();

		model.addAttribute("feed", feed);

		return "home";
	}

	@RequestMapping("/facebookToken")
	public String facebookToken(@RequestParam(required = true, defaultValue = "") String userIdStr) {

		String token = "";

		Integer userId = 0;

		if (StringUtils.isEmpty(userIdStr)) {
			throw new UserException("Please input valid user");
		}

		try {
			userId = Integer.parseInt(userIdStr);
		} catch (Exception e) {
			throw new UserException("Please input valid user");
		}

		AccessToken accessToken = accessTokensService.findByUserId(userId);

		token = accessToken.getToken();

		return token;

	}

	@RequestMapping("/logout")
	public ModelAndView logout(Map<String, Object> model, HttpSession session) {

		if (session != null) {
			session.invalidate();
		}

		SecurityContextHolder.getContext().setAuthentication(null);

		return new ModelAndView("login").addObject("logoutMessage", "You have been logged out.");
	}

}