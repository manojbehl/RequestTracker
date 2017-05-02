package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.Partner;

public class PartnerListMapper implements RowMapper<Partner> {

	public Partner mapRow(ResultSet result, int arg1) throws SQLException {

		Partner partnerList = new Partner();
		partnerList.setPartnerId(result.getInt("partner_id"));
		partnerList.setPartnerName(result.getString("partner_name"));
		partnerList.setStatus(result.getString("title"));
		return partnerList;
	}

}
