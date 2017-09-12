package com.textbookvalet.ui.www.security;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.textbookvalet.commons.SessionVariables;
import com.textbookvalet.commons.User;

@SuppressWarnings("unchecked")
public class UserUtils {

	public static Map<String, Object> getSessionsMap() {
		return (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getCredentials();
	}

	public static User getLoggedInUser() {
		return (User) getSessionsMap().get(SessionVariables.USER);
	}

	public static Integer getLoggedInUserId() {
		return getLoggedInUser().getId();
	}

	public static boolean isUserLoggedIn() {
		try {
			getSessionsMap();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static User getPoserUser() {
		if (getSessionsMap().containsKey(SessionVariables.POSER_USER)) {
			return (User) getSessionsMap().get(SessionVariables.POSER_USER);
		}
		return (User) getLoggedInUser();
	}

	/**
	 * This method returns the user Id of the poser (poser here is the user who
	 * is logged in as another user). If no one is posing as another
	 * user..actual user is returned. This must be used in all the DAO classes
	 * in update and create method for 'modifiedBy' and 'createdBy' fields.
	 * 
	 * @return User Id of the poser (for example Admin Portal user)
	 */
	public static Integer getPoserUserId() {
		try {
			return getPoserUser().getId();
		} catch (Exception e) {
			return -1;
		}
	}

}
