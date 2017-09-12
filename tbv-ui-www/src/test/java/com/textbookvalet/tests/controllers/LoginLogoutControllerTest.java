package com.textbookvalet.tests.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class LoginLogoutControllerTest {

	private static final String USERNAME = "viralpatel79@yahoo.com";
	private static final String PASSWORD = "viral123";
	private static final String BAD_PASSWORD = "xyz";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}

	@Test
	public void testUserLoginNoUserExists() throws Exception {
		mockMvc.perform(post("/doLogin")).andExpect(view().name("login"));
	}

	@Test
	public void testUserLoginBadCredentials() throws Exception {
		mockMvc.perform(post("/doLogin").param("email", USERNAME).param("password", BAD_PASSWORD))
				.andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	public void testUserLoginValidUser() throws Exception {
		mockMvc.perform(post("/doLogin").param("email", USERNAME).param("password", PASSWORD))
				.andExpect(status().isOk()).andExpect(view().name("home"));
	}

}
