package com.textbookvalet.services;

import java.util.List;

import com.textbookvalet.commons.Event;

public interface EventService extends BaseService<Event, Integer> {

	public Event findByschoolIduserId(Integer schoolId, Integer userId);

	public List<Event> findBySchoolId(Integer schoolId);

	public List<Event> findBySchoolIds(List<Integer> schoolIds);

}
