package com.textbookvalet.ui.www.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RentalProblemsController extends BaseController {

	@RequestMapping("/rentalProblems")
	public String welcome(Map<String, Object> model) {
		return "rentalProblem";
	}

}