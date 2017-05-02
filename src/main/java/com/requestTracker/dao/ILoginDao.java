package com.requestTracker.dao;


import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Login;
import com.requestTracker.domain.User;
@Repository
public interface ILoginDao {
	public User validateLogin (Login login);

}
