package com.textbookvalet.rest.v1.controllers;

import java.io.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploads")
public class SchoolImageController {

	public static String getImagePath(String baseUploadPath, int schoolId, String fileName) {
		if (fileName != null) {
			return baseUploadPath + File.separator + "uploads" + File.separator + "school" + File.separator
					+ "off_campus_image" + File.separator + schoolId + File.separator + fileName;
		}
		return null;
	}

}
