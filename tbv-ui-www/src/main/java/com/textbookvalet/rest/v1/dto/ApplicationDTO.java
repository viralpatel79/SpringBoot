package com.textbookvalet.rest.v1.dto;

import java.io.Serializable;
import java.util.List;

import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@CustomResponse
public class ApplicationDTO implements Serializable {

	private String name;

	private String email;

	private String phone;

	private String involvement;

	private String why_work_for;

	private String role;

	private String school_name;

	private String housing;

	private String phone_type;

	private String paypal_email;

	private List<String> tag_list;

	private String state;

	private String referrer_referral_code;

}
