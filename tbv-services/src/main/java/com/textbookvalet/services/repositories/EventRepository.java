package com.textbookvalet.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.textbookvalet.commons.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

	@Query("select e from Event e where e.schoolId = ?1 and e.userId= ?2 and e.deleted=0")
	public Event findByschoolIduserId(int schoolId, int userId);

	@Query("select e from Event e where e.schoolId = ?1 and e.deleted=0")
	public List<Event> findBySchoolId(Integer schoolId);

	@Query("select e from Event e where e.schoolId in (?1) and e.deleted=0")
	public List<Event> findBySchoolIds(List<Integer> schoolId);

}
