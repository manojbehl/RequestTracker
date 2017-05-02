package com.requestTracker.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.requestTracker.dao.IApplicationDao;
import com.requestTracker.dao.IPartnerDao;
import com.requestTracker.dao.IServerRequestDao;
import com.requestTracker.domain.ServerRequest;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.json.ReportFilter;

@Service
public class ServerRequestServiceImpl implements IServerRequestService {
	@Autowired
	IServerRequestDao	serverRequestDao;
	@Autowired
	IPartnerDao	partnerDao;
	@Autowired
	IApplicationDao	applicationDao;
	
	Logger	logger	=	Logger.getLogger(ServerRequestServiceImpl.class);

	public int recordRequest(ServerRequest serverRequest) throws BusinessException {
		int	 recordCount	=	0;
		if(serverRequestDao.isIpWhite(serverRequest)){
			System.out.println("The "+serverRequest.getIp()+"is white");
			recordCount	=	serverRequestDao.addWhiteRequest(serverRequest);
			return	recordCount;
		}
		System.out.println("The "+serverRequest.getIp()+"is black");
		recordCount	=	serverRequestDao.addBlackRequest(serverRequest);
		return	recordCount;

	}

	public List<ReportFilter> getConversionOverviewReportByFilter(ReportFilter	reportFilter) throws BusinessException {
		List<ReportFilter>	partners	=	partnerDao.fetchConversionOverviewReportByFilter(reportFilter);
			return partners;
		}

	public List<ServerRequest> getConversionDetailReportByFilter(ServerRequest serverRequest) throws BusinessException {
		List<ServerRequest>	serverRequestList	=	serverRequestDao.fetchConversionDetailReportByFilter(serverRequest);
		return serverRequestList;
	}
	


	
}

