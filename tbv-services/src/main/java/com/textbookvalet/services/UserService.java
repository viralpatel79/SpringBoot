package com.textbookvalet.services;

import java.util.List;

import com.textbookvalet.commons.User;

public interface UserService extends BaseService<User, Integer> {

	public User findByEmail(String email);

	public User findByAuthProviderAndUID(String authProvider, String authUid);

	public List<User> findAll(Integer page, Integer perPage);

	public List<User> getStaffsBySchoolIdAndRoles(String type, List<String> roles, Integer schoolId);

	public void updatePassword(String epassword, String email);

	public void updateProfilePicture(String name, Integer id);

	public User findActiveUserById(Integer id);

	public int getCountRepsWithCalendars(Integer schoolId);

}
