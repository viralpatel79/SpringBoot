package com.textbookvalet.tests.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class UserRestControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private String BASE_URL = "/profiles";

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetUserProfileById() throws Exception {
		mockMvc.perform(get(BASE_URL + "/" + 1)).andExpect(status().isOk())
				.andExpect(jsonPath("status").value("success"));
	}

	@Test
	public void testGetProfiles() throws Exception {
		mockMvc.perform(get(BASE_URL).param("page", "1").param("per_page", "2")).andExpect(status().isOk())
				.andExpect(jsonPath("status").value("success")).andExpect(jsonPath("data").isArray());

	}

	@Test
	public void testGetProfiles_InvalidPageValue() throws Exception {
		mockMvc.perform(get(BASE_URL).param("page", "0").param("per_page", "2")).andExpect(status().isOk())
				.andExpect(model().attribute("exception",
						Matchers.hasProperty("message", Matchers.equalTo(Constants.INVALID_PAGE_VALUE))));
	}

	@Test
	public void testGetProfiles_InvalidPerPageValue() throws Exception {
		mockMvc.perform(get(BASE_URL).param("page", "1").param("per_page", "-1")).andExpect(status().isOk())
				.andExpect(model().attribute("exception",
						Matchers.hasProperty("message", Matchers.equalTo(Constants.INVALID_PER_PAGE_VALUE))));
	}
}
