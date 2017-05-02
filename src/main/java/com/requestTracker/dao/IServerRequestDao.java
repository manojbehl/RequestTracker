package com.requestTracker.dao;

import java.util.List;
import com.requestTracker.domain.ServerRequest;
import com.requestTracker.exception.BusinessException;

public interface IServerRequestDao {
	

	public boolean isIpWhite(ServerRequest serverRequest)  ;
	public int addWhiteRequest(ServerRequest serverRequest) throws BusinessException;
	public int	addBlackRequest(ServerRequest	serverRequest) throws BusinessException;
	public List<ServerRequest> fetchConversionDetailReportByFilter(ServerRequest serverRequest) throws BusinessException;
	
}

