package com.textbookvalet.rest.v1.dto;

import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@CustomResponse
public @Data class StaffDTO {

	private long id;

	private String email;

	private String firstName;

	private String lastName;

	private String picture;

	private String calendarId;

}
