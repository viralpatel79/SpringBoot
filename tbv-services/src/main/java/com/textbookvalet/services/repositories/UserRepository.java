package com.textbookvalet.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.textbookvalet.commons.User;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("select u from User u where lower(u.email) = lower(?1) and u.deleted = 0")
	public User findByEmailAndPassword(String email);

	@Query("select u from User u where lower(u.authProvider) = lower(?1) and u.authUid = (?2) and u.deleted = 0")
	public User findByAuthProviderAndUID(String authProvider, String authUid);

	@Query("select u from User u where u.type = ?1 and u.role in (?2) and u.schoolId = ?3")
	public List<User> getStaffsBySchoolIdAndRoles(String type, List<String> roles, Integer schoolId);

	@Modifying
	@Query("update User u set u.encryptedPassword = ?1 where u.email = ?2")
	public void updatePassword(String epassword, String email);

	@Modifying
	@Query("update User u set u.profilePicture = ?1 where u.id = ?2")
	public void updateProfilePicture(String name, Integer id);

	@Query("select u from User u where u.referrerId = ?1")
	public List<User> findUsersByReferredId(Integer userId);

	@Query("select u from User u where u.id=?1 and u.deleted = 0")
	public User findActiveUserById(Integer userId);

	@Query("select count(u) from User u where u.type IN ('Admin') AND u.role = 'campusrep' AND u.schoolId = ?1 AND (u.calendarId IS NOT NULL)")
	public int getCountRepsWithCalendars(Integer schoolId);

}
