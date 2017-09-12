package com.textbookvalet.services;

import java.util.List;

import com.textbookvalet.commons.School;

public interface SchoolService extends BaseService<School, Integer> {

	public List<School> findAll(int page, int pre_page);

	public List<School> findBySchoolIds(List<Integer> schoolIds);

}
