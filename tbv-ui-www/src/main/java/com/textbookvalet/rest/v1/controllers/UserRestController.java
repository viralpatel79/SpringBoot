package com.textbookvalet.rest.v1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.v1.dto.UserDTO;
import com.textbookvalet.services.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController("profiles")
@RequestMapping("/profiles")
public class UserRestController {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Operations about profiles")
	@ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "Integer", paramType = "path")
	@GetMapping("/{id}")
	public UserDTO getUserProfileById(@PathVariable("id") Integer id) {
		User user = userService.getById(id);
		return modelMapper.map(user, UserDTO.class);
	}

	@ApiOperation(value = "Operations about profiles")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "Page", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "per_page", value = "Per Page", required = true, dataType = "Integer", paramType = "query") })
	@GetMapping
	public List<UserDTO> getProfiles(@RequestParam("page") Integer page, @RequestParam("per_page") Integer perPage) {

		if (page <= 0) {
			throw new UserException(Constants.INVALID_PAGE_VALUE);
		}

		if (perPage <= 0) {
			throw new UserException(Constants.INVALID_PER_PAGE_VALUE);
		}

		List<User> userList = userService.findAll(page, perPage);

		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User user : userList) {
			userDTOList.add(modelMapper.map(user, UserDTO.class));
		}
		return userDTOList;
	}
}
