package com.requestTracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.requestTracker.domain.Application;
import com.requestTracker.exception.BusinessException;
@Service
public interface IApplicationService {
	
	public List<Application> getAllApps() throws BusinessException;
	public List<Application> getAppsByPartnerId(int partnerId) throws BusinessException;
	public boolean checkAppNameExists(String appName);
}
