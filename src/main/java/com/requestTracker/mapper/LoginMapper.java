package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.User;


public class LoginMapper implements RowMapper<User> {

	public User mapRow(ResultSet result, int arg1) throws  SQLException {
		User userDetail = new User();
		userDetail.setFname(result.getString("first_name"));
		userDetail.setUserEmail(result.getString("user_email"));
		userDetail.setUserId(result.getInt("user_id"));
		userDetail.setPartnerId(result.getInt("partner_id"));
		userDetail.setRole(result.getString("role"));
		return userDetail;
		
	}

}
