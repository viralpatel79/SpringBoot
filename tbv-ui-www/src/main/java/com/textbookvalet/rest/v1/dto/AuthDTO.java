package com.textbookvalet.rest.v1.dto;

import com.textbookvalet.commons.User;
import com.textbookvalet.commons.annotations.CustomResponse;

@CustomResponse
public class AuthDTO {
	
	private String token;
	private User profile;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

}
