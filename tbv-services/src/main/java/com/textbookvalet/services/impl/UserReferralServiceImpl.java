package com.textbookvalet.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.TypesEnum;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.UserReferralData;
import com.textbookvalet.services.UserReferralService;
import com.textbookvalet.services.repositories.ApplicationRepository;
import com.textbookvalet.services.repositories.CashoutRepository;
import com.textbookvalet.services.repositories.OrderRepository;
import com.textbookvalet.services.repositories.UserRepository;
import com.textbookvalet.services.utils.Utility;

@Service
public class UserReferralServiceImpl extends BaseServiceImpl implements UserReferralService {

	private final Logger log = LoggerFactory.getLogger(UserReferralServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CashoutRepository cashoutRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	public UserReferralData getUserReferralData(User user) {

		List<Object[]> result = orderRepository.findTotalEarningsAndTotalReferredLoopSales(user.getId());

		BigDecimal totalEarnings = BigDecimal.ZERO;
		BigDecimal totalReferralLoopSales = BigDecimal.ZERO;

		if (result != null && !result.isEmpty() && result.get(0) != null) {
			Object[] totalEarningsAndReferredLoopSales = result.get(0);

			totalEarnings = totalEarningsAndReferredLoopSales[0] != null
					? (BigDecimal) totalEarningsAndReferredLoopSales[0] : BigDecimal.ZERO;
			totalReferralLoopSales = totalEarningsAndReferredLoopSales[1] != null
					? (BigDecimal) totalEarningsAndReferredLoopSales[1] : BigDecimal.ZERO;
		}

		BigDecimal totalCashouts = cashoutRepository.findTotalCashout(user.getId());

		BigDecimal availableEarnings = totalEarnings.subtract(totalCashouts == null ? BigDecimal.ZERO : totalCashouts);

		Integer referredAdminPendingCount = applicationRepository.findReferredAdminPendingCount(user.getId());

		List<User> referredUserList = userRepository.findUsersByReferredId(user.getId());

		int referredCustomerCount = 0;
		int referredTotalUserCount = 0;
		int referredUserCount = 0;

		for (User referredUser : referredUserList) {
			String referrerType = referredUser.getReferrerType();
			String userType = referredUser.getType();

			if (Constants.REFERRER_TYPE_USER.equalsIgnoreCase(referrerType)) {
				referredTotalUserCount++;

				if (TypesEnum.ADMIN.getValue().equalsIgnoreCase(userType)) {
					referredUserCount++;
				} else if (TypesEnum.CUSTOMER.getValue().equalsIgnoreCase(userType)) {
					referredCustomerCount++;
				}
			}
		}

		log.debug("User referral value calculated successfully for user : " + user.getId());

		UserReferralData userRefData = new UserReferralData(user);

		userRefData.setTotalEarningsFormat(Utility.formatAmt(totalEarnings));
		userRefData.setAvailableEarningsFormat(Utility.formatAmt(availableEarnings));
		userRefData.setTotalCashoutsFormat(Utility.formatAmt(totalCashouts));
		userRefData.setTotalReferralLoopSalesFormat(Utility.formatAmt(totalReferralLoopSales));

		userRefData.setReferredAdminPendingCount(referredAdminPendingCount);
		userRefData.setReferredCustomerCount(referredCustomerCount);
		userRefData.setReferredUserCount(referredUserCount);
		userRefData.setReferredTotalUserCount(referredTotalUserCount);

		log.debug("User referral value formatted successfully for user : " + user.getId());

		return userRefData;
	}
}
