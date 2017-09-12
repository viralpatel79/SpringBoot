package com.textbookvalet.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.textbookvalet.rest.filters.TokenHandler;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@Transactional
public class TokenHandlerTest {

	@Test
	@Rollback
	public void testAuth() {
		Integer userId = 4368;

		TokenHandler tokenHandler = new TokenHandler();

		String token = tokenHandler.createTokenForUser(userId);
		assertNotNull(token);

		Integer newUserId = tokenHandler.parseUserFromToken(token);
		assertNotNull(newUserId);
		assertEquals("User ID", userId, newUserId);
	}
}