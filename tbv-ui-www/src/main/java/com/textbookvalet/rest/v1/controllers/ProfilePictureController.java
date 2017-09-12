package com.textbookvalet.rest.v1.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.textbookvalet.commons.exceptions.UserException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/uploads")
public class ProfilePictureController {

	private final Logger log = LoggerFactory.getLogger(ProfilePictureController.class); 
	 
	@Value("${uploadsPath}")
	private String baseProfilePicurePath; 
	
	public static String getAdminProfilePicturePath(String baseProfilePicurePath, int id, String fileName) {
		return baseProfilePicurePath + File.separator + "uploads" + File.separator + "admin" + 
				File.separator + "profile_picture" + File.separator + id + File.separator + fileName;
	}
	
	public static String getCustomerProfilePicturePath(String baseProfilePicurePath, int id, String fileName) {
		return baseProfilePicurePath + File.separator + "uploads" + File.separator + "customer" + 
				File.separator + "profile_picture" + File.separator + id + File.separator + fileName;
	}

	@ApiOperation(value = "Returns the Profile Picture")
	@RequestMapping(value = "/customer/profile_picture/{id}/{fileName:.+}", method = RequestMethod.GET)
	public @ResponseBody byte[] getCustomerImage(
			@PathVariable("id") Integer id, 
			@PathVariable("fileName") String fileName) throws IOException {
		
		final String profilePicturePath = getCustomerProfilePicturePath(baseProfilePicurePath, id, fileName);
		
		log.debug("Retreving profile picture from the path: " + profilePicturePath); 
		
		try {
			return IOUtils.toByteArray(Files.newInputStream(Paths.get(profilePicturePath)));
		}
		catch(NoSuchFileException e) {		 
	    	throw new UserException("No profile image found by the provided ID and File Name.");
	    } 
	   
	}
	
	@ApiOperation(value = "Returns the Profile Picture")
	@RequestMapping(value = "/admin/profile_picture/{id}/{fileName:.+}", method = RequestMethod.GET)
	public @ResponseBody byte[] getAdminImage(
			@PathVariable("id") Integer id, 
			@PathVariable("fileName") String fileName) throws IOException {
		
		final String profilePicturePath = getAdminProfilePicturePath(baseProfilePicurePath, id, fileName);
		
		log.debug("Retreving profile picture from the path: " + profilePicturePath); 
		
		try {
			return IOUtils.toByteArray(Files.newInputStream(Paths.get(profilePicturePath)));
		}
		catch(NoSuchFileException e) {		 
	    	throw new UserException("No profile image found by the provided ID and File Name.");
	    } 
	   
	}
}
