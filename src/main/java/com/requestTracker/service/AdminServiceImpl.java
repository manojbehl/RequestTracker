package com.requestTracker.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.requestTracker.dao.IAdminDao;
import com.requestTracker.domain.Application;
import com.requestTracker.domain.Login;
import com.requestTracker.domain.Partner;
import com.requestTracker.domain.User;
import com.requestTracker.utils.IPAddressValidator;
import com.requestTracker.utils.KeyGenrator;
import com.requestTracker.utils.PasswordGenrator;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IAdminDao iadminDao;
	Logger logger = Logger.getLogger(AdminServiceImpl.class);

	public List<Partner> getAllPartner() {
		List<Partner> partnerList = iadminDao.getAllPartner();
		if (partnerList != null && partnerList.size() > 0) {
			for (int i = 0; i < partnerList.size(); i++) {
				String status = partnerList.get(i).getStatus();
				if (status.equals("ACTIVE")) {
					partnerList.get(i).setViewStatus("Pause");
				} else {
					partnerList.get(i).setViewStatus("Activate");
				}
			}
			return partnerList;
		}
		return partnerList;
	}

	public String addPartnerUser(User user) {
		Login login = new Login();
		user.setRole("user");
		login.setPassword(PasswordGenrator.generatePswd(8));
		login.setEmail(user.getUserEmail());
		if (!iadminDao.checkEmail(user)) {
			String pwd = iadminDao.addPartnerUser(user, login);
			if (pwd.length() > 0) {
				return pwd;
			}
		}
		return null;
	}

	public List<User> getUserList(int pid) {
		List<User> userList = iadminDao.getUserList(pid);
		if (userList != null) {
			System.out.println("UserList Size " + userList.size());
			return userList;
		}
		return null;
	}

	public List<Partner> getAppIpMap(int PartnerId) {
		List<Partner> getAppIpMap = iadminDao.getAppIpMap(PartnerId);
		if (getAppIpMap != null) {
			for (int i = 0; i < getAppIpMap.size(); i++) {
				String status = getAppIpMap.get(i).getStatus();
				if (status.equals("ACTIVE")) {
					getAppIpMap.get(i).setViewStatus("Pause");
				} else {
					getAppIpMap.get(i).setViewStatus("Active");
				}
			}
			return getAppIpMap;

		}
		return null;
	}

	public boolean updateStatus(User user, int viewStatusId) {
		if (iadminDao.updateStatus(user, viewStatusId)) {
			return true;
		}
		return false;
	}

	public boolean updateUserInfo(User user) {
		if (user.getPassword() != null && user.getPassword().trim() != "") {
			iadminDao.changePassword(user);
		}
		if (iadminDao.updateUserInfo(user)) {
			return true;
		}
		return false;
	}

	public boolean setPartnerStatus(int PartnerId, String viewStatus) {
		Partner partner = new Partner();
		partner.setPartnerId(PartnerId);
		if (viewStatus.equals("Activate")) {
			partner.setStatusId(1);
		} else {
			partner.setStatusId(2);
		}
		boolean statusChanged = iadminDao.setPartnerStatus(partner);
		if (statusChanged) {
			return true;
		}
		return false;
	}

	public Collection<Application> getPartnerApps(int pid) {

		List<Partner> appList = new ArrayList<Partner>();
		appList = iadminDao.getPartnerApps(pid);
		Map<String, Application> mapForApplication = new HashMap<String, Application>();
		for (Iterator<Partner> iterator = appList.iterator(); iterator.hasNext();) {
			Partner partner = iterator.next();
			if (mapForApplication.get(partner.getApplication()) == null) {
				Application application = new Application();
				application.setPartnerId(partner.getPartnerId());
				application.setApp_Name(partner.getApplication());
				application.setAppId(partner.getAppId());
				application.getAppIps().add(partner.getIp());
				application.setAppKey(partner.getAppKeyAray()[0]);
				mapForApplication.put(partner.getApplication(), application);
			} else {
				Application application = mapForApplication.get(partner.getApplication());
				application.getAppIps().add(partner.getIp());
				mapForApplication.put(partner.getApplication(), application);
			}
		}
		return mapForApplication.values();
	}

	public String mapAppIps(int partnerId, String[] appIds, String[] appIps) {
		System.out.println(appIds.length + "\tApp Ips Length" + appIps.length);
		List<Application> editAppList = new ArrayList<Application>();

		for (int i = 0; i < appIds.length; i++) {
			Application editApps = new Application();
			editApps.setAppId(Integer.parseInt(appIds[i]));
			editApps.setPartnerId(partnerId);
			Set<String> ipList = new HashSet<String>();

			if (appIds.length == 1) {
				for (int j = 0; j < appIps.length; j++) {
					String retval = appIps[j];
					if (IPAddressValidator.validate(retval.trim())) {
						logger.info("=============ENTERED  IP WAS VALID :============= " + retval);
						ipList.add(retval.trim());

					} else {
						logger.info("=============You Entered Invalid IP :============= " + retval);
					}
				}
			} else {

				for (String retval : appIps[i].split(",")) {

					if (IPAddressValidator.validate(retval.trim())) {
						logger.info("=============ENTERED  IP WAS VALID :============= " + retval);
						ipList.add(retval.trim());

					} else {
						logger.info("=============You Entered Invalid IP :============= " + retval);
					}
				}

			}
			editApps.setAppIps(ipList);
			editApps.setAppKey(KeyGenrator.partnerAppKeyGenerator(partnerId, Integer.parseInt(appIds[i])));
			editAppList.add(editApps);
		}
		System.out.println(iadminDao.mapAppIps(editAppList));
		return "Success";
	}

	public int createNewApplication(Application application) {

		return iadminDao.createNewApplication(application);

	}

	public List<Partner> getAllActivePartners() {
		List<Partner> partners = new ArrayList<Partner>();
		List<Partner> allPartners = getAllPartner();
		if (allPartners != null) {
			for (Partner partner : allPartners) {
				if (partner.getStatus().equals("ACTIVE")) {
					partners.add(partner);
				}
			}
		}
		return partners;
	}

}
