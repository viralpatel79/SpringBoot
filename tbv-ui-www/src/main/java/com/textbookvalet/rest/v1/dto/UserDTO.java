package com.textbookvalet.rest.v1.dto;

import com.google.gson.annotations.SerializedName;
import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@CustomResponse
public @Data class UserDTO {

	@SerializedName("id")
	private Integer id;

	private String email;

	private String firstName;

	private String lastName;

	@SerializedName("picture")
	private String profilePicture;

	private String calendarId;

}
