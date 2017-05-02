package com.requestTracker.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application implements Serializable {

	private static final long serialVersionUID = 5412999233024731672L;
	private int partnerId;
	private int appId;
	private String app_Name;
	private List<Partner>	partners;
	private Set<String>	appIps;
	private String appKey;
	private String appLogoAddress;
	private String appURL;
	private String status;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Application	app	=	(Application)obj;
		if(app.getAppId()	==	appId){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return appId;
	}
	


	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppURL() {
		return appURL;
	}
	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAppId() {
		return appId;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getApp_Name() {
		return app_Name;
	}

	public void setApp_Name(String app_Name) {
		this.app_Name = app_Name;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	public Set<String> getAppIps() {
		if(appIps==null)
			appIps=new HashSet<String>();
		return appIps;
	}
	public void setAppIps(Set<String> appIps) {
		this.appIps = appIps;
	}
	@Override
	public String toString() {
		return "Application [appId=" + appId + ", app_Name=" + app_Name + ", partners=" + partners + ", appIps="
				+ appIps + "]";
	}
	public String getAppLogoAddress() {
		return appLogoAddress;
	}
	public void setAppLogoAddress(String appLogoAddress) {
		this.appLogoAddress = appLogoAddress;
	}

}
