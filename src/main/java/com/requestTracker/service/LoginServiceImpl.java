package com.requestTracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.requestTracker.dao.ILoginDao;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.User;
@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private ILoginDao ilogindao;
	
	public User validateLogin(Login login) {
		return ilogindao.validateLogin(login);
	}

}
