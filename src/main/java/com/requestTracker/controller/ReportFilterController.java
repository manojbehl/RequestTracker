package com.requestTracker.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.requestTracker.domain.ServerRequest;
import com.requestTracker.domain.User;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.json.ReportFilter;
import com.requestTracker.json.ResponseJSON;
import com.requestTracker.service.IApplicationService;
import com.requestTracker.service.IPartnerService;
import com.requestTracker.service.IServerRequestService;


@RestController
public class ReportFilterController {
	@Autowired
	IPartnerService	partnerService;
	@Autowired
	IApplicationService applicationService;
	@Autowired
	IServerRequestService	serverRequestService;
	private static final Logger logger = Logger.getLogger(ReportFilterController.class);
	
	@GetMapping("/requestReport")
	public ModelAndView	displayRequestReportPage(HttpSession	session) {
		ModelAndView	modelAndView	=	new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelAndView.setViewName("login");

//			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		modelAndView.setViewName("requestReport");
		return modelAndView;
		
	}
	@GetMapping("/partnerReport")
	public ModelAndView	displayPartnerReportPage(HttpSession	session) {
		ModelAndView	modelAndView	=	new ModelAndView();
		User someObject = (User) session.getAttribute("userSessionObject");
		if(someObject==null){
			modelAndView.setViewName("login");
//			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		modelAndView.setViewName("partnerReport");
		return modelAndView;
		
	}

	/*
	 *Handles Request For The 
	 *Conversion overview Report Of Partners  
	*/
		@PostMapping("/report")
	public ResponseJSON getConversionOverview(@RequestBody ReportFilter	reportFilter){
		List<ReportFilter> partnerSetWithAppsAndHits	=	new ArrayList<ReportFilter>();
		ResponseJSON	responseJSON	=	new ResponseJSON();
		logger.info("The recieved object is : "+reportFilter.toString() );
		logger.info("from date "+reportFilter.getFromDate());
		logger.info("to date "+reportFilter.getToDate());
		logger.info("partner id "+reportFilter.getPartnerId());
		logger.info("app id "+reportFilter.getAppId());
	
		try {
			
			/*if(reportFilter.getFromDate().equals("")	||	reportFilter.getFromDate().trim().equals("")	||	reportFilter.getToDate().equals("")	||	reportFilter.getToDate().trim().equals("")){
				throw new BusinessException(ExceptionConstant.INVALID_DATE);
			}
			else{*/
				partnerSetWithAppsAndHits	=	serverRequestService.getConversionOverviewReportByFilter(reportFilter);
			//}
		} catch (BusinessException e) {
			e.printStackTrace();
			responseJSON.setError(true);
			responseJSON.setMessage(e.getExceptionMessage());
			return responseJSON;
		}catch (Exception e) {
			e.printStackTrace();
			responseJSON.setError(true);
			responseJSON.setMessage(e.getMessage());
			return responseJSON;
		}
		responseJSON.setResult(partnerSetWithAppsAndHits);
		return responseJSON;
		
	}
	
	@PostMapping("/reportData")
	public ResponseJSON getConversionDetails(@RequestBody ServerRequest	serverRequest){
		List<ServerRequest> appConversionDataList	=	new ArrayList<ServerRequest>();
		ResponseJSON	responseJSON	=	new ResponseJSON();
		logger.info("Conversion Params : "+serverRequest.toString());
	
		try {
			
			/*if(reportFilter.getFromDate().equals("")	||	reportFilter.getFromDate().trim().equals("")	||	reportFilter.getToDate().equals("")	||	reportFilter.getToDate().trim().equals("")){
				throw new BusinessException(ExceptionConstant.INVALID_DATE);
			}
			else{*/
			appConversionDataList	=	serverRequestService.getConversionDetailReportByFilter(serverRequest);
			//}
		} catch (BusinessException e) {
			e.printStackTrace();
			responseJSON.setError(true);
			responseJSON.setMessage(e.getExceptionMessage());
			return responseJSON;
			//return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseJSON);
		}catch (Exception e) {
			e.printStackTrace();
			responseJSON.setError(true);
			responseJSON.setMessage(e.getMessage());
			return responseJSON;
			//return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseJSON);
		}
		responseJSON.setResult(appConversionDataList);
		return responseJSON;
		//return ResponseEntity.status(HttpStatus.OK).body(responseJSON);
		
	}
		
}
