package com.textbookvalet.rest.v1.dto;

import java.io.Serializable;

import javax.persistence.Transient;

import com.google.gson.annotations.SerializedName;
import com.textbookvalet.commons.BaseTracebleModel;
import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@CustomResponse
@EqualsAndHashCode(callSuper = true)
public @Data class AccountDTO extends BaseTracebleModel implements Serializable {

	@SerializedName("id")
	private Integer id;

	private String email;

	private String role;

	private String firstName;

	private String lastName;

	@SerializedName("picture")
	private String profilePicture;

	private String phone;

	private String address;

	private String bio;

	private String paypalAddress;

	private Integer schoolId;

	@Transient
	@SerializedName("school")
	private SchoolDTO schoolDTO;

	private String classYear;

	private String classOfYear;

	private boolean visible;

	private String referral;

	private String referralAdmin;

	private String referralCode;

	private String referralPath;

	private String jobReferralPath;

	private String sellReferralPath;

	private String buyReferralPath;

	private String earnReferralPath;

	private int reerralAdminPendingCount;

	private int referralUserCount;

}
