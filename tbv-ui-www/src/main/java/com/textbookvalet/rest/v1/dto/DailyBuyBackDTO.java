package com.textbookvalet.rest.v1.dto;

import com.textbookvalet.commons.annotations.CustomResponse;

@CustomResponse
public class DailyBuyBackDTO {

	private String message;

	private String image;

	private String location;

	private String time;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
