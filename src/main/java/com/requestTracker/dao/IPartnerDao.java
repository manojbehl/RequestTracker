package com.requestTracker.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Partner;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.json.ReportFilter;

@Repository
public interface IPartnerDao {
	public boolean doesPartnerExist(Partner	partner);
	public boolean isAPPActiveForPartner(Partner partner);
	public int addNewPartner(Partner partner);
	public boolean mapPartnerWithApps(Partner partner);
	public void mapPartnerWithApps(int partnerId, int appId, String paernerAppKeyGenerator);
	public void removePartnerAppMapping(int partnerId, int appId);
	public String fetchPartnerLogoLocation(int partnerId);
	public boolean doesPartnerWithNameExist(String partnerName);
	/*
	 * get all partners for 
	 * the list (dropdown) on reports page
	 */
	public List<Partner> fetchAllPartnerList() throws BusinessException;
	
	/*
	 * get the converion overview report 
	 * as per the filter params passed from the report page
	*/
	
	public List<ReportFilter> fetchConversionOverviewReportByFilter(ReportFilter	filter) throws BusinessException;
	

}

