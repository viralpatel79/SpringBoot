package com.textbookvalet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.School;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.services.repositories.SchoolRepository;

@Service
public class SchoolServiceImpl extends BaseServiceImpl implements SchoolService {

	@Autowired
	private SchoolRepository schoolRepository;

	@Override
	public List<School> getAll() {
		return (List<School>) schoolRepository.findAll();
	}

	@Override
	public School save(School school) {
		return schoolRepository.save(school);
	}

	@Override
	public School getById(Integer id) {
		return schoolRepository.findOne(id);
	}

	@Override
	public void delete(Integer id) {
		schoolRepository.delete(id);
	}

	@Override
	public List<School> findAll(int page, int pre_page) {
		PageRequest request = new PageRequest(page - 1, pre_page, Sort.Direction.ASC, "id");
		return schoolRepository.findAll(request).getContent();
	}

	@Override
	public List<School> findBySchoolIds(List<Integer> schoolIds) {
		return schoolRepository.findBySchoolIds(schoolIds);
	}

}
