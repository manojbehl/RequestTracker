package com.requestTracker.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.requestTracker.domain.User;

public class PartnerUserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setPartnerId(rs.getInt("partner_id"));
		user.setFname(rs.getString("first_name"));
		user.setLname(rs.getString("last_name"));
		user.setRole(rs.getString("role"));
		user.setUserEmail((rs.getString("user_email")));
		user.setStatusId(rs.getInt("status_id"));
		return user;
	}

}
