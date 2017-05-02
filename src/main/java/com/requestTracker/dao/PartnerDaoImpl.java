package com.requestTracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.requestTracker.domain.Partner;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.exception.ExceptionConstant;
import com.requestTracker.json.ReportFilter;
import com.requestTracker.mapper.PartnerMapper;
import com.requestTracker.mapper.ReportFilterMapper;
import com.requestTracker.utils.DateUtil;

@Repository
public class PartnerDaoImpl implements IPartnerDao {
	@Autowired
	JdbcTemplate	jdbcTemplate;
	Logger	logger	=Logger.getLogger(PartnerDaoImpl.class);
	public boolean doesPartnerExist(Partner	partner) {
		String	partnerExistQuery	=	"SELECT count(partner_id)partner_id FROM partner where partner_id=?";
	
		Integer id =jdbcTemplate.queryForObject(partnerExistQuery, Integer.class, new Object[]{partner.getPartnerId()});
		return	(id.intValue()	>	0);	
	}



	public boolean isAPPActiveForPartner(Partner	partner) {
		//String	partnerExistQuery	=	"SELECT count(sr_no)sr_no FROM partner_app where partner_id=? and app_id=?";
		/*
		Integer id =jdbcTemplate.queryForObject(partnerExistQuery, Integer.class, new Object[]{partner.getPartnerId(), partner.getPartnerApplications().get(0).getAppId()});
		return	(id.intValue()	>	0);*/	
		return true;
	}



	public int addNewPartner(final Partner partner) {
		int partnerId	=	0;
		final String addPartnerQuery	=	"INSERT INTO partner (partner_name,partner_logo_loc) VALUES (?,?)";
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				final PreparedStatement ps = con.prepareStatement(addPartnerQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, partner.getPartnerName());
				ps.setString(2, partner.getPartnerLogoPath());
				return ps;
			}
		};

		final KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);
		partnerId = holder.getKey().intValue();
		return partnerId;
	}



	public boolean mapPartnerWithApps(Partner partner) {
		StringBuffer mapPartnerWithAppsQuery	=	new StringBuffer("insert into partner_app (partner_id,app_id,app_key)values");
		mapPartnerWithAppsQuery.append("("+partner.getPartnerId()+","+partner.getAppIdAray()[0]+",'"+partner.getAppKeyAray()[0]+"')");
		
		if(partner.getAppIdAray().length>1){
			for(int i=1;i<partner.getAppIdAray().length;i++){
				mapPartnerWithAppsQuery.append(",("+partner.getPartnerId()+","+partner.getAppIdAray()[i]+",'"+partner.getAppKeyAray()[i]+"')");	
			}
		}
		logger.info("mapPartnerWithAppsQuery : "+mapPartnerWithAppsQuery);
		
		/*if(partner.getAppIdAray().length>1){
			for(int i=1;i<partner.getAppIdAray().length;i++){
				mapPartnerWithAppsQuery.append(",("+partner.getPartnerId()+","+partner.getAppIdAray()[i]+","+partner.getAppKeyAray()[i]+")");	
			}
			
		}*/	
		
		return (jdbcTemplate.update(new String(mapPartnerWithAppsQuery))	>	0);
		}



	public void mapPartnerWithApps(int partnerId, int appId, String appKey) {
		String mapPartnerWithAppsQuery	=	"insert into partner_app (partner_id,app_id,app_key)values(?,?,?)";
		jdbcTemplate.update(mapPartnerWithAppsQuery, new Object[]{partnerId,appId,appKey});
		
	}



	public void removePartnerAppMapping(int partnerId, int appId) {
		String removePartnerAppMappingQuery	=	"UPDATE partner_app pa LEFT JOIN app_key_ip_mapping akm ON akm.app_key = pa.app_key  SET pa.status_id = 2, akm.status_id = 2 , akm.update_time =current_timestamp() , pa.update =current_timestamp()WHERE  pa.partner_id = ? AND pa.app_id = ?";
		jdbcTemplate.update(removePartnerAppMappingQuery, new Object[]{partnerId,appId});
	}



	public String fetchPartnerLogoLocation(int partnerId) {
		String	partnerLogoLocQuery	=	"Select partner_logo_loc from partner where partner_id=?";
		return jdbcTemplate.queryForObject(partnerLogoLocQuery, new Object[]{partnerId},String.class);
	}
	public boolean doesPartnerWithNameExist(String partnerName) {
		String	partnerExistQuery	=	"SELECT count(partner_id)partner_id FROM partner where partner_name=?";
		return (jdbcTemplate.queryForObject(partnerExistQuery, Integer.class, new Object[]{partnerName})	>	0);
	}
	
	public List<Partner> fetchAllPartnerList() throws BusinessException {
		List<Partner>	partnerList	=	new ArrayList<Partner>();
		
		String partnerSetQuery	=	"SELECT * FROM partner order by partner_name desc";
		partnerList	=	jdbcTemplate.query(partnerSetQuery, new PartnerMapper());
		if(partnerList.size()	!=	0){
			return partnerList;
		}
		throw new BusinessException(ExceptionConstant.EMPTY_ACTIVE_PARTNER_LIST);
	}


	public List<ReportFilter> fetchConversionOverviewReportByFilter(ReportFilter	reportFilter) throws BusinessException {
		List<ReportFilter>	partners	=	new ArrayList<ReportFilter>();
		
		StringBuffer	partnerAppHitQuerry	=	new StringBuffer("SELECT cd.app_key, part.partner_id, part_mas.partner_name, app.app_id, app.app_name, count(1) counter FROM conversion_details cd, partner_app part, partner part_mas, app_master app "
				+ " where part.app_key = cd.app_key "
				+ " AND part.partner_id = part_mas.partner_id "
				+ " AND part.app_id = app.app_id ");
		int prepIndex	=	0;
		
				List<Object> listOfObject = new ArrayList<Object>();
		
				if(reportFilter.getPartnerId()	>	0){
					partnerAppHitQuerry.append(" AND part.partner_id = ? ");
				}
				if(reportFilter.getAppId()	>	0){
					partnerAppHitQuerry.append(" AND part.app_id = ? ");
				}
				if(!reportFilter.getFromDate().trim().equals("")){
					partnerAppHitQuerry.append(" AND cd.create_time >= ? ");
				}
				if(!reportFilter.getToDate().trim().equals("")){
					partnerAppHitQuerry.append(" AND cd.create_time <= ? ");
				}
				
				if(reportFilter.getPartnerId()	!=	0){
					listOfObject.add(prepIndex++, reportFilter.getPartnerId());
				}
				if(reportFilter.getAppId()	!=	0){
					listOfObject.add(prepIndex++, reportFilter.getAppId());
				}
				if(reportFilter.getFromDate()!=null && (!reportFilter.getFromDate().trim().equals(""))){
					listOfObject.add(prepIndex++, DateUtil.convertStringToDateFormat(reportFilter.getFromDate()));
				}
				if(reportFilter.getToDate()!=null && (!reportFilter.getToDate().trim().equals(""))){
					listOfObject.add(prepIndex++, DateUtil.convertStringToDateFormat(reportFilter.getToDate()));
				}
				
				partnerAppHitQuerry.append(" GROUP BY app_key, cd.app_key, part.partner_id, part_mas.partner_name, app.app_id, app.app_name");
				
				
				System.out.println("query : "+partnerAppHitQuerry+"\n index is : "+prepIndex);
				if( listOfObject.size()>0){
					partners	=	jdbcTemplate.query(partnerAppHitQuerry.toString(),listOfObject.toArray(new Object[]{}) , new ReportFilterMapper());
				}
				else{
					partners	=	jdbcTemplate.query(new String(partnerAppHitQuerry), new ReportFilterMapper() );
				}
				
			if(partners.size()	>	0){
				return partners;
			}

		throw new BusinessException(ExceptionConstant.NO_RESULT_FOUND);
	}


}
