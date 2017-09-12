package com.textbookvalet.ui.www.security; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandlerExtended extends SecurityContextLogoutHandler {
	
	//@Autowired LoginLogoutHistoryDao loginLogoutHistoryDao;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
    	/*
    	LoginLogoutHistory history = new LoginLogoutHistory();
		history.setUserId(UserUtils.getLoggedInUserId());
		history.setAction(LoginLogoutHistoryDao.LOGOUT_ACTION);

		loginLogoutHistoryDao.createLoginLogoutHistory(history);
    	
    	super.setClearAuthentication(true);
    	super.setInvalidateHttpSession(true);
        super.logout(request, response, authentication);  
        */ 
    }
}
