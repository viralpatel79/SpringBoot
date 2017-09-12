package com.textbookvalet.commons;

import java.io.Serializable;

import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@CustomResponse
public class UserReferralData implements Serializable {

	private String totalEarningsFormat;

	private String availableEarningsFormat;

	private String totalCashoutsFormat;

	private String totalReferralLoopSalesFormat;

	private String referralCode;

	private String referralPath;

	private String jobReferralPath;

	private String sellReferralPath;

	private String buyReferralPath;

	private String earnReferralPath;

	private int referredTotalUserCount;

	private int referredUserCount;

	private int referredCustomerCount;

	private int referredAdminPendingCount;

	private int refLinkVisitCount;

	public UserReferralData() {
	}

	public UserReferralData(User user) {

		this.refLinkVisitCount = user.getRefLinkVisitCount();

		this.referralCode = user.getReferralCode();

		this.referralPath = "/r/" + this.referralCode;

		this.jobReferralPath = "/j/" + this.referralCode;

		this.sellReferralPath = "/s/" + this.referralCode;

		this.buyReferralPath = "/b/" + this.referralCode;

		this.earnReferralPath = "/e/" + this.referralCode;
	}

}
