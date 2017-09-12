package com.textbookvalet.services;

import com.textbookvalet.commons.Application;

public interface ApplicationService extends BaseService<Application, Long> {

	Application getApplicationByUser(Integer userId);

}
