package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.Application;
import com.requestTracker.domain.Partner;

public class PartnerMapper implements RowMapper<Partner> {

	public Partner mapRow(ResultSet result, int arg1) throws SQLException {
		Partner partner = new Partner();
		Application application = new Application();

		application.setAppId(result.getInt("app_id"));
		if (result.getString("app_name") != null) {
			application.setApp_Name(result.getString("app_name"));
		}

		partner.setPartnerId(result.getInt("partner_id"));
		//partner.getPartnerIps().add(result.getString("ip"));
		partner.setAppHitCount(result.getInt("hit_count"));
		if (result.getString("partner_name") != null) {
			partner.setPartnerName(result.getString("partner_name"));
		}
		//partner.getPartnerApplications().add(application);
		partner.getPartnerApplications().add(application);
		return partner;
	}

}
