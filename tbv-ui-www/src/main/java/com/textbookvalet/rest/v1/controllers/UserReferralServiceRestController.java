package com.textbookvalet.rest.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.UserReferralData;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.services.UserReferralService;
import com.textbookvalet.services.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController("Operations about users")
@RequestMapping("/users")
public class UserReferralServiceRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserReferralService userReferralService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Validates identity through JWT provided in auth/login", required = true, dataType = "String", paramType = "header"),
			@ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "Integer", paramType = "path") })
	@GetMapping("/{id}/referral_data")
	public UserReferralData getUserReferralData(@PathVariable("id") Integer id) {

		User user = userService.findActiveUserById(id);
		if (user == null) {
			throw new UserException(Constants.ACTIVE_REC_NOT_FOUND);
		}

		return userReferralService.getUserReferralData(user);
	}
}
