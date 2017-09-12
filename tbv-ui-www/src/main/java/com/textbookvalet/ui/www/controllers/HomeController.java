package com.textbookvalet.ui.www.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.textbookvalet.commons.User;
import com.textbookvalet.ui.www.security.UserUtils;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends BaseController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String root(Map<String, Object> model, HttpSession session) {
		return "redirect:login";
	}

	@RequestMapping("/home")
	public String home(Map<String, Object> model, HttpSession session) {
		User user = UserUtils.getLoggedInUser();

		model.put("message", this.message);
		model.put("username", user.getEmail());

		return "home";
	}

	@RequestMapping(value="/manifest", method = RequestMethod.GET)
	public String manifest(){
		String rc = "";
		Package warPackage = Package.getPackage("org.springframework.boot.loader.jar");
		rc += "Implementation Title: " + warPackage.getImplementationTitle() + "<br />" +
				"Implementation Version: " + warPackage.getImplementationVersion() + "<br />";
		return rc;
	}
}