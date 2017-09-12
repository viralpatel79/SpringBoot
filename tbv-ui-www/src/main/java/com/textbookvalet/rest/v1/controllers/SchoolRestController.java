package com.textbookvalet.rest.v1.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.textbookvalet.commons.Event;
import com.textbookvalet.commons.RolesEnum;
import com.textbookvalet.commons.School;
import com.textbookvalet.commons.TypesEnum;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.v1.dto.DailyBuyBackDTO;
import com.textbookvalet.rest.v1.dto.SchoolDTO;
import com.textbookvalet.rest.v1.dto.SchoolRequestDTO;
import com.textbookvalet.rest.v1.dto.StaffDTO;
import com.textbookvalet.services.EventService;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.services.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/schools")
public class SchoolRestController {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(SchoolRestController.class);

	@Autowired
	SchoolService schoolService;

	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${uploadsPath}")
	private String baseUploadPath;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "Provide page number to retrive", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "per_page", value = "Provide number of records per page to retrive", required = true, dataType = "Integer", paramType = "query") })
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<SchoolDTO> getAll(@ModelAttribute SchoolRequestDTO dto, HttpServletRequest request,
			HttpServletResponse response) {

		List<SchoolDTO> list = new ArrayList<SchoolDTO>();

		List<School> schoolList = schoolService.findAll(dto.getPage(), dto.getPer_page());

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		List<Integer> ids = new ArrayList<Integer>();

		for (School school : schoolList) {
			ids.add(school.getId());
		}

		for (School school : schoolList) {

			SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);

			DailyBuyBackDTO dailyBuyBackDTO = new DailyBuyBackDTO();

			dailyBuyBackDTO.setMessage(school.getBuyBack());
			dailyBuyBackDTO.setImage(
					SchoolImageController.getImagePath(baseUploadPath, school.getId(), school.getOffCampusImage()));
			dailyBuyBackDTO.setLocation(school.getOffCampusWhere());
			dailyBuyBackDTO.setTime(school.getOffCampusTime());

			schoolDTO.setDailyBuyback(dailyBuyBackDTO);
			schoolDTO.setFutureEvent(new ArrayList<Event>());

			int repsWithCalendarsCount = userService.getCountRepsWithCalendars(school.getId());
			if (repsWithCalendarsCount > 0) {
				schoolDTO.setRepsWithCalendars(true);
			}

			list.add(schoolDTO);
		}

		return list;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Validates identity through JWT provided in auth/login", required = true, dataType = "String", paramType = "header"),
			@ApiImplicitParam(name = "id", value = "School ID", required = true, dataType = "String", paramType = "path") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody SchoolDTO getSchoolById(@PathVariable Integer id) {

		if (id <= 0) {
			throw new UserException("Please provide valid shcool id.");
		}

		School school = schoolService.getById(id);

		if (school == null) {
			throw new UserException("Please provide valid shcool id.");
		}

		DailyBuyBackDTO dailyBuyBackDTO = new DailyBuyBackDTO();

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);

		dailyBuyBackDTO.setMessage(school.getBuyBack());
		dailyBuyBackDTO.setImage(
				SchoolImageController.getImagePath(baseUploadPath, school.getId(), school.getOffCampusImage()));
		dailyBuyBackDTO.setLocation(school.getOffCampusWhere());
		dailyBuyBackDTO.setTime(school.getOffCampusTime());

		schoolDTO.setDailyBuyback(dailyBuyBackDTO);
		schoolDTO.setFutureEvent(new ArrayList<Event>());

		int repsWithCalendarsCount = userService.getCountRepsWithCalendars(school.getId());
		if (repsWithCalendarsCount > 0) {
			schoolDTO.setRepsWithCalendars(true);
		}

		return schoolDTO;

	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Validates identity through JWT provided in auth/login", required = true, dataType = "String", paramType = "header"),
			@ApiImplicitParam(name = "id", value = "School ID", required = true, dataType = "String", paramType = "path") })
	@RequestMapping(value = "/{id}/staffs", method = RequestMethod.GET)
	public @ResponseBody List<StaffDTO> getStaffsBySchoolId(@PathVariable Integer id,
			@RequestParam(name = "roles", required = false) List<String> roles) {

		if (id <= 0) {
			throw new UserException("Please provide valid shcool id.");
		}

		List<String> roleList = new ArrayList<String>();

		if (roles == null) {
			for (RolesEnum role : RolesEnum.values()) {
				if (role.getId() != RolesEnum.CUSTOMER.getId()) {
					roleList.add(role.getValue());
				}
			}
		} else {
			roleList = roles;
		}

		List<StaffDTO> list = new ArrayList<StaffDTO>();

		List<User> staffsList = userService.getStaffsBySchoolIdAndRoles(TypesEnum.ADMIN.getValue(), roleList, id);

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		for (User staff : staffsList) {

			StaffDTO staffDTO = modelMapper.map(staff, StaffDTO.class);

			staffDTO.setPicture(ProfilePictureController.getAdminProfilePicturePath(baseUploadPath, staff.getId(),
					staff.getImage()));

			list.add(staffDTO);
		}

		return list;

	}
}
