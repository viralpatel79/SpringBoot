package com.textbookvalet.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.textbookvalet.commons.School;

@Repository
public interface SchoolRepository extends PagingAndSortingRepository<School, Integer> {

	/*
	 * @Query("select a from AccessToken a where a.userId = ?1") public School
	 * findByUserId(long userId);
	 */

	@Query("select s from School s where s.id in (?1)")
	public List<School> findBySchoolIds(List<Integer> schoolIds);

}
