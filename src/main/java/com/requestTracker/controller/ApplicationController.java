package com.requestTracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.requestTracker.domain.Application;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.json.ResponseJSON;
import com.requestTracker.service.IApplicationService;

@RestController
public class ApplicationController {
	
	@Autowired
	IApplicationService	applicationService;
	

	@GetMapping(value = "/apps")
	public ResponseJSON getAllApps()  {
		ResponseJSON	responseJSON	=	 new ResponseJSON();
		List <Application> appList	=	new ArrayList<Application>();
		try {
		appList	=	applicationService.getAllApps();
		
			} catch (BusinessException e) {
				e.printStackTrace();
				responseJSON.setError(true);
				responseJSON.setMessage(e.getExceptionMessage());
				return responseJSON;
			}
		responseJSON.setResult(appList);
		return responseJSON;
		
	}

}
