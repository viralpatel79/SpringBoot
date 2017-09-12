package com.textbookvalet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.Application;
import com.textbookvalet.services.ApplicationService;
import com.textbookvalet.services.repositories.ApplicationRepository;

@Service
public class ApplicationServiceImpl extends BaseServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public List<Application> getAll() {
		return (List<Application>) applicationRepository.findAll();
	}

	@Override
	public Application save(Application application) {
		return applicationRepository.save(application);
	}

	@Override
	public Application getById(Long id) {
		return applicationRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		applicationRepository.delete(id);
	}

	@Override
	public Application getApplicationByUser(Integer userId) {
		List<Application> applicationList = applicationRepository.findApplicationByUserId(userId);
		return applicationList.isEmpty() ? null : applicationList.get(0);
	}

}
