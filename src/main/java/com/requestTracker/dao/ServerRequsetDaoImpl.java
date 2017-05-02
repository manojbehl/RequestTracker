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
import com.requestTracker.domain.ServerRequest;
import com.requestTracker.exception.BusinessException;
import com.requestTracker.exception.ExceptionConstant;
import com.requestTracker.mapper.ServerRequestMapper;
import com.requestTracker.utils.DateUtil;


@Repository
public class ServerRequsetDaoImpl implements IServerRequestDao {
	@Autowired
	JdbcTemplate	jdbcTemplate;

	Logger	logger	=	Logger.getLogger(ServerRequsetDaoImpl.class);


	
	public int addWhiteRequest(final ServerRequest serverRequest) throws BusinessException {
		int conversionId = 0;
		String addWhiteRequestQuery	=	"INSERT INTO conversion_details (ip,app_key,req_url) VALUES (?,?,?)";
		conversionId	=	 jdbcTemplate.update(addWhiteRequestQuery, new Object[]{serverRequest.getIp(), serverRequest.getApplicationKey(), serverRequest.getConversionUrl()});
		if (conversionId > 0) {
			
			logger.info("#######The Conversion was recorded: " );
			return conversionId;
		}
		return conversionId;
	}

	
	public boolean isIpWhite(ServerRequest serverRequest) {
		String isIpWhiteQuery	=	"select count(sr_no)sr_no from app_key_ip_mapping where ip=? and app_key=? and status_id=1;";
		
		//System.out.println("the query is	:\n	"+isIpWhiteQuery);
		System.out.println("The serverRequest information in dao  is :\n"+serverRequest.getIp()+" AND "+serverRequest.getApplicationKey());
		
			Integer isWhite	=	jdbcTemplate.queryForObject(isIpWhiteQuery, new Object[]{serverRequest.getIp(),serverRequest.getApplicationKey()}, Integer.class);
			if(isWhite.intValue()	==	1){
				System.out.println("Match found Ip is white ");
				return true;
		}
		System.out.println("inside dao IP is unknown");
		return false;
	}

	
	public int addBlackRequest(final ServerRequest serverRequest) throws BusinessException {
		int requestId = 0;
		final PreparedStatementCreator psc = new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				final PreparedStatement ps = con.prepareStatement("INSERT INTO blacklisted_req_details (ip, req_url) VALUES (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, serverRequest.getIp());
				ps.setString(2, serverRequest.getConversionUrl());
				return ps;
			}
		};

		final KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, holder);
		requestId = holder.getKey().intValue();
		if (requestId > 0) {
			System.out.println("The generated unknown Request ID is : " + requestId);
			throw new BusinessException(ExceptionConstant.HOST_BLACKLISTED);
		}
		throw	new  BusinessException(ExceptionConstant.REQUEST_NOT_RECORDED);
	}



	public boolean doesPartnerExist(int partnerId) {
		String	partnerExistQuery	=	"SELECT count(partner_id)partner_id FROM partner where partner_id=?";
	
		Integer id =jdbcTemplate.queryForObject(partnerExistQuery, Integer.class, new Object[]{partnerId});
		return	(id.intValue()	>	0);	
	}


	public List<ServerRequest> fetchConversionDetailReportByFilter(ServerRequest serverRequest) throws BusinessException {
		List<ServerRequest>	serverRequests	=	new ArrayList<ServerRequest>();
		List<Object> listOfObject = new ArrayList<Object>();
		StringBuffer conversionDetailsQuery	=	new StringBuffer("SELECT cd.conversion_id, cd.ip, cd.req_url, DATE_FORMAT(cd.create_time, '%d-%b-%Y  %r')create_time, part.partner_id, part_mas.partner_name, app.app_id, app.app_name FROM conversion_details cd, partner_app part, partner part_mas, app_master app  WHERE part.app_key = cd.app_key AND part.partner_id = part_mas.partner_id AND part.app_id = app.app_id ");
				if(serverRequest.getPartnerId()		>	0){
					conversionDetailsQuery.append(" AND part.partner_id = ? ");
				}
				if(serverRequest.getAppid()		>	0){
					conversionDetailsQuery.append("  AND part.app_id = ? ");
				}
				if(serverRequest.getConversionTime()	!=	null	&&	(!	serverRequest.getConversionTime().trim().equals(""))){
					conversionDetailsQuery.append(" AND cd.create_time >= ? ");
				}
				if(serverRequest.getConversionToTime()	!=	null && (!serverRequest.getConversionToTime().trim().equals(""))){
					conversionDetailsQuery.append(" AND cd.create_time <= ? ");
				}
				
				if(serverRequest.getPartnerId()		>	0){
					listOfObject.add(serverRequest.getPartnerId());
				}
				if(serverRequest.getAppid()	>	0){
					listOfObject.add(serverRequest.getAppid());
				}
				if(serverRequest.getConversionTime()	!=	null	&&	(!	serverRequest.getConversionTime().trim().equals(""))){
					listOfObject.add(DateUtil.convertStringToDateFormat(serverRequest.getConversionTime()));
				}
				if(serverRequest.getConversionToTime()	!=	null 	&& (!	serverRequest.getConversionToTime().trim().equals(""))){
					listOfObject.add(DateUtil.convertStringToDateFormat(serverRequest.getConversionToTime()));
				}
				
		
		if( listOfObject.size()>0){
			serverRequests	=	jdbcTemplate.query(new String(conversionDetailsQuery), listOfObject.toArray(new Object[]{}), new ServerRequestMapper());
		}
		else{
			serverRequests	=	jdbcTemplate.query(new String(conversionDetailsQuery), new ServerRequestMapper() );
		}
		
		return serverRequests;
	}


}
