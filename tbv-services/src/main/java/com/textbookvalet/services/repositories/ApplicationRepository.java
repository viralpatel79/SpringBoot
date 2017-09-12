package com.textbookvalet.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.textbookvalet.commons.Application;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

	List<Application> findApplicationByUserId(Integer userId);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM applications WHERE applications.referrer_id = ?1 "
			+ "AND lower(applications.referrer_type) = 'user' " + "AND (lower(applications.aasm_state) != 'hired') "
			+ "AND (lower(applications.aasm_state) != 'archived')")
	Integer findReferredAdminPendingCount(Integer user);
}
