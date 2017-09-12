package com.textbookvalet.rest.v1.dto;

import lombok.Data;

public @Data class AccountRequestDTO {

	private String token;

	private String firstName;

	private String lastName;

	private String phone;

	private Integer schoolId;

	private String address;

	private String bio;

	private String paypalAddress;

	private String classYear;

	private String classOfYear;

	private boolean visible;

}
