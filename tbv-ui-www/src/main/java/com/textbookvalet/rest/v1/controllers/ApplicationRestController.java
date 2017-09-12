package com.textbookvalet.rest.v1.controllers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.exceptions.SystemException;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.v1.dto.ApplicationDTO;

import io.swagger.annotations.ApiImplicitParam;

@RestController("Operations about applications")
@RequestMapping("/applications")
public class ApplicationRestController {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(ApplicationRestController.class);
 
	@Value("${railsAPI.baseURL}")
	private String railsBaseUrl; 

	@ApiImplicitParam(name = "Authorization", value = "Validates identity through JWT provided in auth/login", required = true, dataType = "String", paramType = "header")
	@GetMapping
	public ApplicationDTO getUserApplicationByAuth(HttpServletRequest req) throws UnirestException {
		String token = req.getHeader(Constants.AUTH_HEADER_NAME);

		HttpResponse<JsonNode> response = Unirest.get(railsBaseUrl + "applications")
				.header("accept", "application/json").header(Constants.AUTH_HEADER_NAME, token).asJson();

		return prepareResponse(response); 
	}

	@PostMapping
	public ApplicationDTO createApplication(@ModelAttribute ApplicationDTO application, HttpServletRequest req)
			throws UnirestException {
		String token = req.getHeader(Constants.AUTH_HEADER_NAME);

		// State will be change by change state api
		application.setState(null);

		Gson gson = new Gson();
		JsonElement reqJson = new Gson().toJsonTree(application);

		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> jsonMap = gson.fromJson(reqJson, type);

		Map<String, Object> formDataMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
			if (entry.getKey().equals("tag_list")) {
				@SuppressWarnings("unchecked")
				List<String> value = (List<String>) entry.getValue();

				formDataMap.put("application[" + entry.getKey() + "]", StringUtils.join(value, ','));
			} else {
				formDataMap.put("application[" + entry.getKey() + "]", entry.getValue());
			}
		}

		HttpResponse<JsonNode> response = Unirest.post(railsBaseUrl + "applications")
				.header("accept", "application/json").header(Constants.AUTH_HEADER_NAME, token)
				.header("Content-Type", "application/x-www-form-urlencoded").fields(formDataMap).asJson();

		return prepareResponse(response);
	}

	@PostMapping("/change_state")
	public ApplicationDTO changeState(@ModelAttribute ApplicationDTO application, HttpServletRequest req)
			throws UnirestException {
		String token = req.getHeader(Constants.AUTH_HEADER_NAME);

		HttpResponse<JsonNode> response = Unirest.post(railsBaseUrl + "applications/change_state")
				.header("accept", "application/json").header(Constants.AUTH_HEADER_NAME, token)
				.header("Content-Type", "application/x-www-form-urlencoded").field("state", application.getState())
				.asJson();

		return prepareResponse(response); 
	}

	private ApplicationDTO prepareResponse(HttpResponse<JsonNode> response) {
		if (response.getStatus() == 200 || response.getStatus() == 201) {
			JSONObject json = response.getBody().getObject().getJSONObject("data");
			return new Gson().fromJson(json.toString(), ApplicationDTO.class);
		} else if (response.getStatus() == 400 || response.getStatus() == 401) {
			JSONObject json = response.getBody().getObject();
			throw new UserException(json.getString("message"));
		} else {
			throw new SystemException();
		}
	}
}
