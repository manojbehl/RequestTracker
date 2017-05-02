package com.requestTracker.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.requestTracker.domain.Application;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;

@Service
public interface IAdminService {
	public List<Partner> getAllPartner();
	public String addPartnerUser(User user);
	public List<User> getUserList( int pid);
	public boolean updateStatus(User user, int viewStatusId);
	public boolean updateUserInfo(User user);
	public List<Partner> getAppIpMap(int PartnerId);
	public boolean setPartnerStatus(int PartnerId , String viewStatus);
	public List<Partner> getAllActivePartners();
	public Collection<Application> getPartnerApps(int id);
	public String mapAppIps(int partnerId,String appId[],String appIps[]);

	public int createNewApplication (Application application);

}
