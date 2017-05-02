package com.requestTracker.dao;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Login;
import com.requestTracker.domain.User;
import com.requestTracker.mapper.LoginMapper;

@Repository
public class LoginDaoImpl implements ILoginDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public User validateLogin(Login login) {
		List<User> validlogin = new ArrayList<User>();
		User userobj = new User();
		String validateLoginQuery = "SELECT first_name,user_email,user_id,partner_id,role FROM login l,userdetail u WHERE binary l.email =? AND binary l.password =? AND l.email = u.user_email and u.status_id=1 ";
		String partnerStatuscheck = "SELECT COUNT(partner_id) FROM partner WHERE partner_id = ? AND status_id = 1 ";
		validlogin=jdbcTemplate.query(validateLoginQuery,new Object[]{login.getEmail(),login.getPassword()},new LoginMapper());
		if(validlogin.size()>0){
			userobj= (User)validlogin.get(0);
			
			if(userobj.getPartnerId()	==	0){
				return userobj;
			}
			else if (jdbcTemplate.queryForObject(partnerStatuscheck,new Object[]{userobj.getPartnerId()}, Integer.class)>0){
				return userobj;
			}
		}
		userobj	=	null;
		return userobj;
	}

}
