package com.textbookvalet.tests.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.textbookvalet.commons.Cashout;
import com.textbookvalet.commons.Order;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.UserReferralData;
import com.textbookvalet.services.impl.UserReferralServiceImpl;
import com.textbookvalet.services.repositories.ApplicationRepository;
import com.textbookvalet.services.repositories.CashoutRepository;
import com.textbookvalet.services.repositories.OrderRepository;
import com.textbookvalet.services.repositories.UserRepository;
import com.textbookvalet.services.utils.Utility;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@Transactional
public class UserReferralServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CashoutRepository cashoutRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private UserReferralServiceImpl userReferralServiceImpl;

	@Test
	@Rollback
	public void testGetUserReferralData() {

		// Data Setup
		User user = getUser("admin", "user1@gmail.com");
		user = userRepository.save(user);

		User admin = getUser("admin", "referredUser1@gmail.com");
		admin.setReferrerId(user.getId());
		admin = userRepository.save(admin);

		User customer = getUser("customer", "referredUser2@gmail.com");
		customer.setReferrerId(user.getId());
		customer = userRepository.save(customer);

		Order order1 = getOrder(user, admin, 4, 1);
		orderRepository.save(order1);

		Order order2 = getOrder(user, customer, 6, 2);
		orderRepository.save(order2);

		Cashout cashout = getCashout(user);
		cashoutRepository.save(cashout);

		com.textbookvalet.commons.Application application = getApplication(user, admin);
		applicationRepository.save(application);

		// Action
		UserReferralData userRefData = userReferralServiceImpl.getUserReferralData(user);

		// Assert
		assertEquals("/r/" + user.getReferralCode(), userRefData.getReferralPath());

		assertEquals("/j/" + user.getReferralCode(), userRefData.getJobReferralPath());

		assertEquals("/s/" + user.getReferralCode(), userRefData.getSellReferralPath());

		assertEquals("/b/" + user.getReferralCode(), userRefData.getBuyReferralPath());

		assertEquals("/e/" + user.getReferralCode(), userRefData.getEarnReferralPath());

		assertEquals(10, userRefData.getRefLinkVisitCount());

		assertEquals("refcode", userRefData.getReferralCode());

		assertEquals(1, userRefData.getReferredAdminPendingCount());

		assertEquals(Utility.formatAmt(BigDecimal.TEN), userRefData.getTotalEarningsFormat());

		assertEquals(Utility.formatAmt(new BigDecimal(3)), userRefData.getTotalReferralLoopSalesFormat());

		assertEquals(1, userRefData.getReferredUserCount());

		assertEquals(1, userRefData.getReferredCustomerCount());

		assertEquals(2, userRefData.getReferredTotalUserCount());

		assertEquals(Utility.formatAmt(new BigDecimal(9)), userRefData.getAvailableEarningsFormat());

	}

	private Cashout getCashout(User user) {
		Cashout c = new Cashout();
		c.setAdminId(user.getId());
		c.setAmount(1);
		return c;
	}

	private Order getOrder(User user, User admin, int commission, int total) {
		Order o = new Order();
		o.setReferrerCommission(commission);
		o.setTotal(total);
		o.setUserId(admin.getId());
		o.setUser(user);
		o.setDeliveryPriceCurrency("USD");
		return o;
	}

	public User getUser(String type, String email) {
		User user = new User();
		user.setRefLinkVisitCount(10);
		user.setReferralCode("refcode");
		user.setReferrerType("user");
		user.setType(type);
		user.setFirstName("Hitesh Test");
		user.setLastName("Hitesh Test");
		user.setEmail(email);
		user.setEncryptedPassword("teststets");
		user.setPassword("teststets");
		return user;
	}

	public com.textbookvalet.commons.Application getApplication(User referingUser, User user) {
		com.textbookvalet.commons.Application app = new com.textbookvalet.commons.Application();
		app.setReferrerId(referingUser.getId());
		app.setReferrerType("user");
		app.setState("On-Boarding");
		app.setUserId(user.getId());
		return app;
	}
}
