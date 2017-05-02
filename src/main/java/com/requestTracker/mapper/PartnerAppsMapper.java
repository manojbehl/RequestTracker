package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.requestTracker.domain.Partner;

public class PartnerAppsMapper implements RowMapper<Partner> {

	public Partner mapRow(ResultSet rs, int rowNum) throws SQLException {
		Partner partnerApps = new Partner();
		String[] appKey={rs.getString("app_key")};
		partnerApps.setAppId(rs.getInt("app_id"));
		partnerApps.setApplication((rs.getString("app_name")));
		partnerApps.setIp(rs.getString("ip"));
		partnerApps.setAppKeyAray(appKey);;
		return partnerApps;
	}

}
