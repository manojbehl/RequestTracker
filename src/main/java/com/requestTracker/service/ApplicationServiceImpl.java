package com.requestTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.requestTracker.dao.IApplicationDao;
import com.requestTracker.domain.Application;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.exception.ExceptionConstant;

@Service
public class ApplicationServiceImpl implements IApplicationService {
	
	@Autowired
	IApplicationDao	applicationDao;
	
	List<Application>	applications	=	null;
	
	public List<Application> getAllApps() throws BusinessException{
		applications	=	applicationDao.fetchAllApps();
		if(applications	!=	null	&&	applications.size()>0)
		{
			return applications;	
		}
		throw new BusinessException(ExceptionConstant.NO_DATA_FOUND);
		
	}

	public List<Application> getAppsByPartnerId(int partnerId) throws BusinessException {
		applications	=	applicationDao.fetchAppsByPartnerId(partnerId);
		return applications;
	}

	public boolean checkAppNameExists(String appName) {
		if(applicationDao.checkAppNameExists(appName)){
			return true;
		}
		return false;
		
	}

}
