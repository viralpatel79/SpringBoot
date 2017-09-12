package com.textbookvalet.tests.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.User;
import com.textbookvalet.rest.filters.TokenHandler;
import com.textbookvalet.ui.www.security.AuthenticationManagerExtended;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class AccountRestControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private AuthenticationManagerExtended authManager;

	@Autowired
	private TokenHandler tokenHandler;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	private String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJfaWQiOjQzNjh9fQ.qAMXnOiTvM2QQ5fwFwE78Rz_osi04VaDt7A_VKjh4xA";

	private String BASE_URL = "/account";

	private String STATUS = "status";
	private String DATA = "data";
	private String SUCESS = "success";

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetAccountProfile() throws Exception {

		User user = testRequestUser();

		mockMvc.perform(get(BASE_URL + "/profile").requestAttr(Constants.AUTHENTICATED_USER, user))
				.andExpect(status().isOk()).andExpect(jsonPath(STATUS).value(SUCESS));
	}

	public User testRequestUser() throws Exception {
		String username = "viralpatel79@yahoo.com";
		String password = "viral123";

		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

		auth = authManager.authenticate(auth);

		User user = (User) BeanUtils.cloneBean((User) auth.getPrincipal());

		return user;

	}

	/*
	 * @Test public void testResetPassword() throws Exception {
	 * mockMvc.perform(get(BASE_URL +
	 * "/reset_password")).andExpect(status().isOk())
	 * .andExpect(jsonPath("status").value("success")); }
	 */
}
