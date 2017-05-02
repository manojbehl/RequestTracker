package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.requestTracker.domain.Partner;

public class AppIpListMapper implements RowMapper<Partner>{

	public Partner mapRow(ResultSet result, int arg1) throws SQLException {
		Partner appIpList = new Partner();
		appIpList.setApplication(result.getString("app_name"));
		appIpList.setIp(result.getString("ip"));
		appIpList.setStatus(result.getString("title"));
		return appIpList;
	}
}
