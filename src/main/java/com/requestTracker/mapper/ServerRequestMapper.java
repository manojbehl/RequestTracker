package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.ServerRequest;

public class ServerRequestMapper implements RowMapper<ServerRequest> {

	
	public ServerRequest mapRow(ResultSet result, int arg1) throws SQLException {
		ServerRequest	serverRequest	=	new	ServerRequest();
		if(!result.getString("ip").isEmpty());{
			serverRequest.setIp(result.getString("ip"));
		}
		if(!result.getString("create_time").isEmpty());{
			serverRequest.setConversionTime(result.getString("create_time"));
		}
		serverRequest.setConversionId(result.getInt("conversion_id"));
		serverRequest.setConversionUrl(result.getString("req_url"));
		return serverRequest;
	}

}
