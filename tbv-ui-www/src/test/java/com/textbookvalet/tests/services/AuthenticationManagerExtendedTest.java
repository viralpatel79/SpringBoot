package com.textbookvalet.tests.services;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.textbookvalet.commons.User;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.utils.PasswordUtil;
import com.textbookvalet.ui.www.security.AuthenticationManagerExtended;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@Transactional
public class AuthenticationManagerExtendedTest {
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManagerExtended authManager;  

	@Test
	@Rollback
	public void testAuth() {
		
		User user = userService.save(getUser());
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		auth = authManager.authenticate(auth); 
		
		try {
			auth = new UsernamePasswordAuthenticationToken(null, user.getPassword());
			auth = authManager.authenticate(auth); 
			
	        fail("Expected an BadCredentialsException to be thrown for null username");
	    } catch (BadCredentialsException badCredentialsException) {
	        assertEquals(AuthenticationManagerExtended.INVALID_USERNAME_PASSWORD, badCredentialsException.getMessage());
	    }
		
		try {
			auth = new UsernamePasswordAuthenticationToken("fake@email.com", null);
			auth = authManager.authenticate(auth); 
			
	        fail("Expected an BadCredentialsException to be thrown for null password");
	    } catch (BadCredentialsException badCredentialsException) {
	        assertEquals(AuthenticationManagerExtended.PASSWORD_FIELD_EMPTY, badCredentialsException.getMessage());
	    } 
		
		try {
			auth = new UsernamePasswordAuthenticationToken("fake@email.com", user.getPassword());
			auth = authManager.authenticate(auth); 
			
	        fail("Expected an BadCredentialsException to be thrown for wrong email and password");
	    } catch (BadCredentialsException badCredentialsException) {
	        assertEquals(AuthenticationManagerExtended.INVALID_USERNAME_PASSWORD, badCredentialsException.getMessage());
	    }
	} 

	private User getUser() {
		User user = new User();
		user.setFirstName("Viral Test");
		user.setLastName("Patel Test");
		user.setEmail("viral@viral.com");
		user.setEncryptedPassword(PasswordUtil.encryptPassword("testPassword"));
		user.setPassword("testPassword");
		return user;
	}  
}