package com.requestTracker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Partner implements Serializable {

	private static final long serialVersionUID = -1985268951629869426L;
	private int partnerId;
	private int appId;
	private String partnerName;
	private String partnerLogoPath;
	
	  private List<ServerRequest> partnerServerRequests = new ArrayList<ServerRequest>(); 
	  private List<String> partnerIps = new ArrayList<String>(); 
	  private List<Application> partnerApplications = new ArrayList<Application>();
	 
	private int[] appIdAray;
	private String[] appKeyAray;
	private int appHitCount;
	private int appCount;
	private int userCount;
	private String status;
	private String viewStatus;
	private String application;
	private String ip;
	private int statusId;

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public int getAppHitCount() {
		return appHitCount;
	}

	public void setAppHitCount(int appHitCount) {
		this.appHitCount = appHitCount;
	}

	public int getAppCount() {
		return appCount;
	}

	public void setAppCount(int appCount) {
		this.appCount = appCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Partner [partnerId=" + partnerId + ", partnerName=" + partnerName + ", appHitCount=" + appHitCount
				+ ", appCount=" + appCount + ", userCount=" + userCount + ", status=" + status + ", viewStatus="
				+ viewStatus + ", application=" + application + ", ip=" + ip + ", statusId=" + statusId + "]";
	}

	public int[] getAppIdAray() {
		return appIdAray;
	}

	public void setAppIdAray(int[] appIdAray) {
		this.appIdAray = appIdAray;
	}

	public String[] getAppKeyAray() {
		return appKeyAray;
	}

	public void setAppKeyAray(String[] appKeyAray) {
		this.appKeyAray = appKeyAray;
	}

	public String getPartnerLogoPath() {
		return partnerLogoPath;
	}

	public void setPartnerLogoPath(String partnerLogoPath) {
		this.partnerLogoPath = partnerLogoPath;
	}

	public List<Application> getPartnerApplications() {
		return partnerApplications;
	}

	public void setPartnerApplications(List<Application> partnerApplications) {
		this.partnerApplications = partnerApplications;
	}

	public List<ServerRequest> getPartnerServerRequests() {
		return partnerServerRequests;
	}

	public void setPartnerServerRequests(List<ServerRequest> partnerServerRequests) {
		this.partnerServerRequests = partnerServerRequests;
	}

	public List<String> getPartnerIps() {
		return partnerIps;
	}

	public void setPartnerIps(List<String> partnerIps) {
		this.partnerIps = partnerIps;
	}

}
