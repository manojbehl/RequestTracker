package com.requestTracker.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Application;
import com.requestTracker.mapper.ApplicationMapper;

@Repository
public class ApplicationDaoImpl implements IApplicationDao {
	@Autowired
	JdbcTemplate	jdbcTemplate;
	
	private List<Application>	applications	=	null;


	public List<Application> fetchAllApps() {
		String allAppQuery = "SELECT app_id,app_name FROM app_master where status_id=1 order by app_name asc";
		applications	=	jdbcTemplate.query(allAppQuery, new ApplicationMapper());
		return applications;
	}


	public List<Application> fetchAppsByPartnerId(int partnerId) {
		String allAppQuery = "SELECT pap.app_id, am.app_name FROM partner_app pap, app_master am where pap.app_id=am.app_id and pap.partner_id=? and pap.status_id=1 order by app_name asc";
		applications	=	jdbcTemplate.query(allAppQuery, new Object[]{partnerId},new ApplicationMapper());
		return applications;
	}


	public  boolean checkAppNameExists(String appName) {
		String allAppQuery = "SELECT count(app_id) FROM app_master where app_name=? ";
		int appId = jdbcTemplate.queryForObject(allAppQuery, new Object[] { appName },Integer.class);
		if(appId==0){
			return true;
		}
		return false;
		
	}


}
