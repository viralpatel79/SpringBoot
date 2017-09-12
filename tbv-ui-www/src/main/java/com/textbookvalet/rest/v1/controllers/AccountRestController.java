package com.textbookvalet.rest.v1.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.Event;
import com.textbookvalet.commons.School;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.rest.v1.dto.AccountDTO;
import com.textbookvalet.rest.v1.dto.AccountRequestDTO;
import com.textbookvalet.rest.v1.dto.DailyBuyBackDTO;
import com.textbookvalet.rest.v1.dto.FileUploadDTO;
import com.textbookvalet.rest.v1.dto.SchoolDTO;
import com.textbookvalet.services.EventService;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.utils.PasswordUtil;

@RestController
@RequestMapping("/account")
public class AccountRestController {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(AccountRestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private EventService eventService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${uploadsPath}")
	private String baseProfilePicurePath;

	@GetMapping("/profile")
	public @ResponseBody AccountDTO getAccountProfiles(HttpServletRequest request) {

		User authenticatedUser = (User) request.getAttribute(Constants.AUTHENTICATED_USER);

		if (authenticatedUser == null) {
			throw new UserException("Invalid User, please provide correct authorization token!");
		}

		User user = userService.getById(authenticatedUser.getId());

		if (user == null) {
			throw new UserException("Invalid User.");
		}

		List<Integer> schoolIds = new ArrayList<Integer>();
		schoolIds.add(user.getSchoolId());

		List<School> schoolList = schoolService.findBySchoolIds(schoolIds);

		Map<Integer, School> schoolMap = schoolList.stream().collect(Collectors.toMap(School::getId, School -> School));

		List<Event> eventList = eventService.findBySchoolIds(schoolIds);
		Map<Integer, List<Event>> eventsMap = new HashMap<Integer, List<Event>>();

		for (Event event : eventList) {
			if (eventsMap.containsKey(event.getSchoolId())) {
				eventsMap.get(event.getSchoolId()).add(event);
			} else {
				List<Event> list = new LinkedList<Event>();
				list.add(event);

				eventsMap.put(event.getSchoolId(), list);
			}
		}

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		AccountDTO accountDTO = modelMapper.map(user, AccountDTO.class);

		if (schoolMap.containsKey(user.getSchoolId())) {

			School school = schoolMap.get(user.getSchoolId());
			SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);

			if (eventsMap.containsKey(school.getId())) {
				schoolDTO.setFutureEvent(eventsMap.get(school.getId()));
			}

			DailyBuyBackDTO dailyBuyBackDTO = new DailyBuyBackDTO();

			dailyBuyBackDTO.setMessage(school.getBuyBack());
			dailyBuyBackDTO.setImage(school.getBuyBackImage());
			dailyBuyBackDTO.setLocation("");
			dailyBuyBackDTO.setTime("");

			schoolDTO.setDailyBuyback(dailyBuyBackDTO);

			accountDTO.setSchoolDTO(schoolDTO);
		}

		return accountDTO;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody AccountDTO save(@ModelAttribute User user, HttpServletRequest request) {

		User authenticatedUser = (User) request.getAttribute(Constants.AUTHENTICATED_USER);

		if (authenticatedUser == null) {
			throw new UserException("Invalid User, please provide correct authorization token!");
		}

		validateUser(user);
		validatePassword(-1, user.getPassword());

		user.setEncryptedPassword(PasswordUtil.encryptPassword(user.getPassword()));

		List<Integer> schoolIds = new ArrayList<Integer>();
		schoolIds.add(user.getSchoolId());

		List<School> schoolList = schoolService.findBySchoolIds(schoolIds);

		Map<Integer, School> schoolMap = schoolList.stream().collect(Collectors.toMap(School::getId, School -> School));

		List<Event> eventList = eventService.findBySchoolIds(schoolIds);
		Map<Integer, List<Event>> eventsMap = new HashMap<Integer, List<Event>>();

		for (Event event : eventList) {
			if (eventsMap.containsKey(event.getSchoolId())) {
				eventsMap.get(event.getSchoolId()).add(event);
			} else {
				List<Event> list = new LinkedList<Event>();
				list.add(event);

				eventsMap.put(event.getSchoolId(), list);
			}
		}

		User newUser = userService.save(user);

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		AccountDTO accountDTO = modelMapper.map(newUser, AccountDTO.class);

		if (schoolMap.containsKey(user.getSchoolId())) {

			School school = schoolMap.get(user.getSchoolId());
			SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);

			if (eventsMap.containsKey(school.getId())) {
				schoolDTO.setFutureEvent(eventsMap.get(school.getId()));
			}

			DailyBuyBackDTO dailyBuyBackDTO = new DailyBuyBackDTO();

			dailyBuyBackDTO.setMessage(school.getBuyBack());
			dailyBuyBackDTO.setImage(school.getBuyBackImage());
			dailyBuyBackDTO.setLocation("");
			dailyBuyBackDTO.setTime("");

			schoolDTO.setDailyBuyback(dailyBuyBackDTO);

			accountDTO.setSchoolDTO(schoolDTO);
		}

		return accountDTO;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.PUT)
	public @ResponseBody AccountDTO update(@ModelAttribute AccountRequestDTO accountRequestDTO,
			HttpServletRequest request) {

		User authenticatedUser = (User) request.getAttribute(Constants.AUTHENTICATED_USER);

		if (authenticatedUser == null) {
			throw new UserException("Invalid User, please provide correct authorization token!");
		}

		User user = userService.getById(authenticatedUser.getId());

		if (user == null) {
			throw new UserException("Invalid User.");
		}

		user.setFirstName(accountRequestDTO.getFirstName());
		user.setLastName(accountRequestDTO.getLastName());
		user.setPhone(accountRequestDTO.getPhone());
		user.setSchoolId(accountRequestDTO.getSchoolId());
		user.setAddress(accountRequestDTO.getAddress());
		user.setBio(accountRequestDTO.getBio());
		user.setPaypalAddress(accountRequestDTO.getPaypalAddress());
		user.setClassOfYear(accountRequestDTO.getClassOfYear());
		user.setVisible(accountRequestDTO.isVisible());

		validateUser(user);

		List<Integer> schoolIds = new ArrayList<Integer>();
		schoolIds.add(user.getSchoolId());

		List<School> schoolList = schoolService.findBySchoolIds(schoolIds);

		Map<Integer, School> schoolMap = schoolList.stream().collect(Collectors.toMap(School::getId, School -> School));

		List<Event> eventList = eventService.findBySchoolIds(schoolIds);
		Map<Integer, List<Event>> eventsMap = new HashMap<Integer, List<Event>>();

		for (Event event : eventList) {
			if (eventsMap.containsKey(event.getSchoolId())) {
				eventsMap.get(event.getSchoolId()).add(event);
			} else {
				List<Event> list = new LinkedList<Event>();
				list.add(event);
				eventsMap.put(event.getSchoolId(), list);
			}
		}
		
		AccountDTO accountDTO = new AccountDTO();
		
		if(schoolList != null && !schoolList.isEmpty()) {
			user.setSchool(schoolList.get(0));
	
			User updatedUser = userService.save(user);
	
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
	
			accountDTO = modelMapper.map(updatedUser, AccountDTO.class);
	
			if (schoolMap.containsKey(user.getSchoolId())) {
	
				School school = schoolMap.get(user.getSchoolId());
				SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);
	
				if (eventsMap.containsKey(school.getId())) {
					schoolDTO.setFutureEvent(eventsMap.get(school.getId()));
				}
	
				DailyBuyBackDTO dailyBuyBackDTO = new DailyBuyBackDTO();
	
				dailyBuyBackDTO.setMessage(school.getBuyBack());
				dailyBuyBackDTO.setImage(school.getBuyBackImage());
				dailyBuyBackDTO.setLocation("");
				dailyBuyBackDTO.setTime("");
	
				schoolDTO.setDailyBuyback(dailyBuyBackDTO);
	
				accountDTO.setSchoolDTO(schoolDTO);
			}
		}

		return accountDTO;
	}

	@RequestMapping(value = "/reset_password", method = RequestMethod.POST)
	public @ResponseBody void resetPassword(@ModelAttribute User user) {

		if (StringUtils.isEmpty(user.getEmail())) {
			throw new UserException("Please provide user's email address.");
		}

		user.setPassword(PasswordUtil.generatePassword());
		user.setEncryptedPassword(PasswordUtil.encryptPassword(user.getPassword()));

		userService.updatePassword(user.getEncryptedPassword(), user.getEmail());

	}

	private void validateUser(User user) {

		if (userService.findByEmail(user.getEmail()) != null && user.getId() == null) {
			throw new UserException("User with email '" + user.getEmail() + "' is already an existing User.");
		}

		if (StringUtils.isEmpty(user.getType())) {
			throw new UserException("User Type field can not be empty.");
		}

		if (StringUtils.isEmpty(user.getRole())) {
			throw new UserException("User Role field can not be empty.");
		}

		if (StringUtils.isEmpty(user.getEmail())) {
			throw new UserException("User Email field can not be empty.");
		}

		if (StringUtils.isEmpty(user.getFirstName())) {
			throw new UserException("Firstname field can not be empty.");
		}

		if (StringUtils.isEmpty(user.getLastName())) {
			throw new UserException("Lastname field can not be empty.");
		}

		if (StringUtils.isEmpty(user.getPhone())) {
			throw new UserException("Phone field can not be empty.");
		}

		if (user.getPhone().length() < 10) {
			throw new UserException("Phone can not be less than ten digits.");
		}

		if (user.getSchoolId() == null || user.getSchoolId() < 0) {
			throw new UserException("School ID can not be empty.");
		}

	}

	public void validatePassword(int userId, String password) {

		if (StringUtils.isBlank(password)) {
			throw new UserException("Password field can not be empty.");
		}
		if (password.length() < 8) {
			throw new UserException("Password can not be less than eight charactors.");
		}
		if (password.length() > 128) {
			throw new UserException("Password can not be greater than one hundred and twenty eight charactors.");
		}

	}

	@RequestMapping(value = "/profile_picture", method = RequestMethod.POST)
	public @ResponseBody FileUploadDTO updateProfilePicture(@RequestParam("uploadFile") MultipartFile file,
			HttpServletRequest request) throws IOException {

		User authenticatedUser = (User) request.getAttribute(Constants.AUTHENTICATED_USER);

		if (authenticatedUser == null) {
			throw new UserException("invalid User, please provide correct authorization token!");
		}

		User user = userService.getById(authenticatedUser.getId());

		if (user == null) {
			throw new UserException("invalid User.");
		}

		FileUploadDTO fileDTO = new FileUploadDTO();

		if (file.isEmpty()) {
			throw new UserException("Please select a file to upload.");
		}

		byte[] bytes = file.getBytes();

		String filename = file.getOriginalFilename();

		String profilePicturePath = ProfilePictureController.getAdminProfilePicturePath(baseProfilePicurePath,
				user.getId(), filename);

		File f = new File(profilePicturePath);
		if (!f.exists()) {
			f.mkdirs();
			if (!f.createNewFile()) {
				f.delete();
				f.createNewFile();
			}
		}

		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(profilePicturePath)));
		stream.write(bytes);
		stream.close();

		userService.updateProfilePicture(filename, user.getId());

		fileDTO.setUddated(true);
		fileDTO.setUrl(profilePicturePath);
		fileDTO.setSize(file.getSize());

		return fileDTO;

	}

}
