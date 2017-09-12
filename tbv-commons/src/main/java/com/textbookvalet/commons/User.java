package com.textbookvalet.commons;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings({ "serial" })
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseTracebleModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SerializedName("id")
	private Integer id;

	private String email;

	private String encryptedPassword;

	private String resetPasswordToken;

	private Timestamp resetPasswordSentAt;

	private Timestamp rememberCreatedAt;

	private int signInCount = 0;

	private Timestamp currentSignInAt;

	private Timestamp lastSignInAt;

	private String currentSignInIp;

	private String lastSignInIp;

	@Column(columnDefinition = "ENUM('customer', 'admin', 'owner', 'operations_manager', 'campusmanager', 'campusrep')")
	private String role = "customer";

	private String firstName;

	private String lastName;

	private String phone;

	private Timestamp startDate = new Timestamp(new Date().getTime());

	private Timestamp endDate;

	@Type(type = "text")
	private String address;

	@Type(type = "text")
	private String bio;

	private String type = "Customer";

	private String stripeCustomerId;

	private Boolean visible = true;

	@Type(type = "text")
	private String referral;

	private Integer referralAdmin;

	private Integer schoolId;

	private String image;

	private String paypalAddress;

	private String repLink;

	private String classOfYear = "Freshman";

	private String profilePicture;

	private String facebookProfilePicture;

	private String aasmState;

	private Boolean api = false;

	private Integer managerId;

	private String calendarId;

	private Boolean availableForAppointment = true;

	@Column(name = "return_address_1")
	private String returnAddress1;

	@Column(name = "return_address_2")
	private String returnAddress2;

	private String returnZipCode;

	private String returnCity;

	private String returnState;

	private String fullName;

	private Integer referrerId;

	private int refLinkVisitCount = 0;

	private String authProvider;

	private String authUid;

	private String referralCode;

	private String referrerType;

	@Transient
	private String password;

	@Transient
	private School school;

}
