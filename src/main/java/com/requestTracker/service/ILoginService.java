package com.requestTracker.service;

import org.springframework.stereotype.Service;

import com.requestTracker.domain.Login;
import com.requestTracker.domain.User;

@Service
public interface ILoginService {
	public User validateLogin(Login login);

}
