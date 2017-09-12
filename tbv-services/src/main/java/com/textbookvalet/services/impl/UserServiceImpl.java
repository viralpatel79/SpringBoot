package com.textbookvalet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.User;
import com.textbookvalet.services.UserService;
import com.textbookvalet.services.repositories.UserRepository;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User save(User User) {
		return userRepository.save(User);
	}

	@Override
	public User getById(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmailAndPassword(email);
	}

	@Override
	public User findByAuthProviderAndUID(String authProvider, String authUid) {
		return userRepository.findByAuthProviderAndUID(authProvider, authUid);
	}

	@Override
	public List<User> findAll(Integer page, Integer perPage) {
		PageRequest pageable = new PageRequest(page - 1, perPage, Direction.ASC, "id");
		return userRepository.findAll(pageable).getContent();

	}

	@Override
	public List<User> getStaffsBySchoolIdAndRoles(String type, List<String> roles, Integer schoolId) {
		return userRepository.getStaffsBySchoolIdAndRoles(type, roles, schoolId);
	}

	@Override
	public void updatePassword(String epassword, String email) {
		userRepository.updatePassword(epassword, email);
	}

	@Override
	public void updateProfilePicture(String name, Integer id) {
		userRepository.updateProfilePicture(name, id);
	}

	@Override
	public User findActiveUserById(Integer id) {
		return userRepository.findActiveUserById(id);
	}

	@Override
	public int getCountRepsWithCalendars(Integer schoolId) {
		return userRepository.getCountRepsWithCalendars(schoolId);
	}
}
