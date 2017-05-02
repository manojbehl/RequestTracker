package com.requestTracker.service;

import java.util.List;
import com.requestTracker.domain.ServerRequest;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.json.ReportFilter;


public interface IServerRequestService {
	
	public	int	recordRequest(ServerRequest	serverRequest) throws BusinessException;	
	public List<ReportFilter> getConversionOverviewReportByFilter(ReportFilter	reportFilter) throws BusinessException;
	public List<ServerRequest> getConversionDetailReportByFilter(ServerRequest serverRequest) throws BusinessException;
	
	

}
