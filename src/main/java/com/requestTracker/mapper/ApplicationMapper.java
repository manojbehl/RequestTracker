package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.Application;

public class ApplicationMapper implements RowMapper<Application> {

	public Application mapRow(ResultSet result, int arg1) throws SQLException {
		Application	application	=	new Application();
		application.setAppId(result.getInt("app_id"));
		application.setApp_Name(result.getString("app_name"));
		return application;
	}

}
