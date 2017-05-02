package com.requestTracker.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Application;

@Repository
public interface IApplicationDao {
	
	public List<Application> fetchAllApps();

	public List<Application> fetchAppsByPartnerId(int partnerId);
	public boolean checkAppNameExists(String appName);
}
