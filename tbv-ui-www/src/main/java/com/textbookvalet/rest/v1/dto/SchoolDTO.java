package com.textbookvalet.rest.v1.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.textbookvalet.commons.Event;
import com.textbookvalet.commons.annotations.CustomResponse;

import lombok.Data;

@CustomResponse
public @Data class SchoolDTO {

	@SerializedName("id")
	private Integer id;

	private String name;

	private String fullName;

	private String about;

	private List<Event> futureEvent;

	private DailyBuyBackDTO dailyBuyback;

	private boolean repsWithCalendars;

	private boolean appointmentsEnabled;

}
