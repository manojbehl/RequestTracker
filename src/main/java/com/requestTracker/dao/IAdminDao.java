package com.requestTracker.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.requestTracker.domain.Application;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;

@Repository
public interface IAdminDao {
	public List<Partner> getAllPartner();
	public String addPartnerUser(User user,Login login);
	public List<User> getUserList(int pid);
	public boolean updateStatus(User user , int viewStatusId);
	public boolean updateUserInfo(User user);
	public List<Partner> getAppIpMap(int PartnerId);
	public boolean setPartnerStatus(Partner partner);

	public List<Partner> getPartnerApps(int id);
	public String mapAppIps(List<Application> editAppList);
	public int createNewApplication (Application application);
	public void changePassword(User user);
	public boolean checkEmail(User user);
}
