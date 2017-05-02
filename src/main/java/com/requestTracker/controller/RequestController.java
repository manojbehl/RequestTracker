package com.requestTracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.requestTracker.domain.ServerRequest;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.exception.ExceptionConstant;
import com.requestTracker.json.ResponseJSON;
import com.requestTracker.service.IServerRequestService;



@RestController
public class RequestController {

	@Autowired
	IServerRequestService serverRequestService;
	
	public static final	Logger	logger	=	Logger.getLogger(RequestController.class);
	/*
	 * handles all the requests from 
	 * servers for different apps 
	*/
	@GetMapping("/sniff/{applicationKey}")
	public  ResponseEntity<ResponseJSON> sniffRequest(@PathVariable("applicationKey") String applicationKey, HttpServletRequest	httpServletRequest){
		ServerRequest	serverRequest	=	new	ServerRequest();
		ResponseJSON	responseJSON	=	new	ResponseJSON();
		String appKey, appUrl;
		try {
		if(applicationKey.isEmpty()	||	applicationKey.trim().isEmpty()){
			//logger.debug(serverRequestJSON.getApplicationKey());
				throw new BusinessException(ExceptionConstant.INVALID_APPLICATION_KEY);
			}
		//	Will be required when th3e method is supposed to be of type POST
		/*if(serverRequestJSON.getRequestUrl().isEmpty()	||	serverRequestJSON.getRequestUrl().trim().isEmpty()){
				throw new BusinessException(ExceptionConstant.INVALID_REQUEST_URL);
			}*/
		appKey	=	applicationKey;
		appUrl	=	"www.picsaxis.com";
		//logger.info(appKey);
		//logger.info(appUrl);
		
		serverRequest.setApplicationKey(appKey);
		serverRequest.setConversionUrl(appUrl);
		logger.info("The request was made by : HOST : "+httpServletRequest.getRemoteHost()+" \nwith  ip : "+httpServletRequest.getHeader("X-Forwarded-For")+" --- "+httpServletRequest.getRemoteAddr()+" \nfor requestURL "+httpServletRequest.getRequestURL());
		
		serverRequest.setIp(httpServletRequest.getHeader("X-Forwarded-For"));
		if (httpServletRequest.getHeader("X-Forwarded-For") == null) {
			serverRequest.setIp(httpServletRequest.getRemoteAddr());
		}
		serverRequestService.recordRequest(serverRequest);
		responseJSON.setMessage("Request Recorded...");
		return ResponseEntity.status(HttpStatus.OK).body(responseJSON);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseJSON.setError(true);
			responseJSON.setMessage(e.getExceptionMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseJSON);
		}
	}
		
	
}

	