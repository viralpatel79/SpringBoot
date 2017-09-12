package com.textbookvalet.ui.www.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.exceptions.SystemException;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.ui.www.springboot.Application;

@ControllerAdvice
class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String DEFAULT_ERROR_VIEW = "globalError";
	private static final String DEFAULT_LOGIN_VIEW = "login";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
			Exception exception) throws Exception {

		if (request.getServletPath().startsWith(Application.BASE_REST_API)) {

			String message = "";
			if (exception instanceof AuthenticationException) {
				message = exception.getMessage();
			} else if (exception instanceof UserException) {
				log.debug(String.format("User exception occured: %s ", exception.getMessage()));
				message = exception.getMessage();
			} else if (exception instanceof UnirestException) {
				log.error(String.format("Calling rails api failed : %s ", exception.getMessage()));
				message = Constants.SYSTEM_EXCEPTION_MESSAGE;
			} else {
				message = Constants.SYSTEM_EXCEPTION_MESSAGE;
				log.error("System exception occured: ", exception);
			}

			RestErrorResponse res = new RestErrorResponse();
			res.setStatus("fail");
			Data data = new Data();
			data.setMessage(message);
			res.setData(data);

			return JsonView.Render(res, response);
		}

		ModelAndView mav = new ModelAndView();

		Exception finalException = exception;
		if (exception instanceof AuthenticationException) {
			finalException = new UserException(exception.getMessage());
			mav.addObject("exception", finalException);
			mav.addObject("url", request.getRequestURL());
			mav.setViewName(DEFAULT_LOGIN_VIEW);

			return mav;
		}

		if (exception instanceof UserException) {
			log.debug(String.format("User exception occured: %s ", exception.getMessage()));
		} else {
			finalException = new SystemException(Constants.SYSTEM_EXCEPTION_MESSAGE);
			log.error("System exception occured: ", exception);
		}

		mav.addObject("exception", finalException);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);

		return mav;
	}
}