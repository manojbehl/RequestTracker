package com.requestTracker.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ReportFilter {

	private int partnerId;
	private	int appId;
	private	String	fromDate;
	private String	toDate;
	private String appName;
	private String partnerName;
	private int appHits;
	
	
	
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getFromDate() {
		
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getAppHits() {
		return appHits;
	}
	public void setAppHits(int appHits) {
		this.appHits = appHits;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Override
	public String toString() {
		return "ReportFilter [partnerId=" + partnerId + ", appId=" + appId + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", appName=" + appName + ", partnerName=" + partnerName + ", appHits=" + appHits + "]";
	}
	
	
	
	
}
