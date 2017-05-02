package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.json.ReportFilter;

public class ReportFilterMapper implements RowMapper<ReportFilter> {

	public ReportFilter mapRow(ResultSet result, int arg1) throws SQLException {
		ReportFilter	reportFilter	=	new ReportFilter();
		
		reportFilter.setAppId(result.getInt("app_id"));
		reportFilter.setPartnerId(result.getInt("partner_id"));
		reportFilter.setPartnerName(result.getString("partner_name"));
		reportFilter.setAppName(result.getString("app_name"));
		reportFilter.setAppHits(result.getInt("counter"));
		
		
		
		return reportFilter;
	}

}
