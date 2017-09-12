package com.textbookvalet.tests.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.textbookvalet.commons.User;

public class UserTest {

	@Test
	public void test() {

		User user = new User();
		user.setId(5);
		user.setEmail("viral");

		assertEquals("Wrong Name", "viral", user.getEmail());
		assertEquals("Wrong Id", 5, user.getId().intValue());
	}

}
