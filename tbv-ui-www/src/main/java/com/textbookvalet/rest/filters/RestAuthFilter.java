package com.textbookvalet.rest.filters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textbookvalet.commons.Constants;
import com.textbookvalet.commons.User;
import com.textbookvalet.commons.exceptions.UserException;
import com.textbookvalet.services.UserService;
import com.textbookvalet.ui.www.springboot.Application;

import io.jsonwebtoken.SignatureException;

@Component
public class RestAuthFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(RestAuthFilter.class);

	private static final List<String> EXCLUDED_URLS = new LinkedList<String>();
	private static final List<String> INCLUDED_URLS = new LinkedList<String>();

	@Autowired
	private UserService userService;

	@Autowired
	private TokenHandler tokenHandler;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		EXCLUDED_URLS.add("/auth");
		EXCLUDED_URLS.add("/schools");
		EXCLUDED_URLS.add("/swagger");
		EXCLUDED_URLS.add("/api-docs");
		EXCLUDED_URLS.add("/webjars"); 
		EXCLUDED_URLS.add("/uploads");
		
		INCLUDED_URLS.add("/schools/");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final String path = request.getServletPath() + request.getPathInfo();

		if (path.startsWith(Application.BASE_REST_API)) {
			
			boolean foundIncluded = false;
			for (String includedUrl : INCLUDED_URLS) {
				if (path.contains(includedUrl)) {
					foundIncluded = true;
				}
			}
			
			if(foundIncluded == false) {
				for (String excludedUrl : EXCLUDED_URLS) {
					if (path.contains(excludedUrl)) {
						chain.doFilter(req, res);
						return;
					}
				}
			}

			Integer userId = getAuthenticatedUserId(request);

			log.debug("REST Request came in for the User ID: " + userId);

			if (userId == null || userId <= 0) {
				throw new UserException("Invalid token presented for url: " + path);
			}

			User user = userService.getById(userId);

			if (user == null) {
				throw new UserException("Invalid token presented.");
			}

			request.setAttribute(Constants.AUTHENTICATED_USER, user);

			log.debug("Successfully processed the authentication token for the user: " + userId);
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

	public Integer getAuthenticatedUserId(HttpServletRequest request) {

		String token = request.getHeader(Constants.AUTH_HEADER_NAME);

		if (token != null) {
			try {
				return tokenHandler.parseUserFromToken(token);

			} catch (SignatureException signEx) {
				log.info(signEx.getMessage());
			} catch (Exception ex) {
				log.info(ex.getMessage());
			}
		}

		return 0;
	}

}