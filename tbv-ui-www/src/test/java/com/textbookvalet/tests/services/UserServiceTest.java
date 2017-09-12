package com.textbookvalet.tests.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.textbookvalet.commons.User;
import com.textbookvalet.services.UserService;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@Transactional
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	@Rollback
	public void testGetAllUsers() {
		userService.save(getUser());
		List<User> allCities = userService.getAll();
		assertTrue(allCities.size() > 0);
	}

	private User getUser() {
		User user = new User();
		user.setFirstName("Viral Test");
		user.setLastName("Patel Test");
		user.setEmail("viral@viral.com");
		user.setEncryptedPassword("teststets");
		user.setPassword("teststets");
		return user;
	}
}
