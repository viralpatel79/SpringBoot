package com.textbookvalet.tests.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.User;
import com.textbookvalet.services.UserService;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class UserReferralRestControllerTest {

	private String baseURL = "/users";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private UserService userService;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetUserReferralData() throws Exception {
		Integer id = new Integer(2);
		Mockito.doReturn(new User()).when(userService).findActiveUserById(id);

		mockMvc.perform(get(baseURL + "/" + id + "/referral_data")).andExpect(status().isOk());
	}

	@Test
	public void testGetUserReferralData_ActiveUserNotFound() throws Exception {
		mockMvc.perform(get(baseURL + "/" + 3 + "/referral_data")).andExpect(model().attribute("exception",
				Matchers.hasProperty("message", Matchers.equalTo(Constants.ACTIVE_REC_NOT_FOUND))));

	}

}
