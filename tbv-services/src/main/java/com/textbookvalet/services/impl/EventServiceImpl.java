package com.textbookvalet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.Event;
import com.textbookvalet.services.EventService;
import com.textbookvalet.services.repositories.EventRepository;

@Service
public class EventServiceImpl extends BaseServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public List<Event> getAll() {
		return (List<Event>) eventRepository.findAll();
	}

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public Event getById(Integer id) {
		return eventRepository.findOne(id);
	}

	@Override
	public void delete(Integer id) {
		eventRepository.delete(id);
	}

	@Override
	public Event findByschoolIduserId(Integer schoolId, Integer userId) {
		return eventRepository.findByschoolIduserId(schoolId, userId);
	}

	@Override
	public List<Event> findBySchoolId(Integer schoolId) {
		return eventRepository.findBySchoolId(schoolId);
	}

	@Override
	public List<Event> findBySchoolIds(List<Integer> schoolIds) {
		return eventRepository.findBySchoolIds(schoolIds);
	}

}
