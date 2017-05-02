package com.requestTracker.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ServerRequest implements Serializable{
	
	private static final long serialVersionUID = 5744377326091921905L;
	private int conversionId;
	private String ip;
	private String applicationKey;
	private String conversionUrl;
	private String conversionTime;
	private String conversionToTime;
	private	Timestamp	conversionTimestamp;
	private int partnerId;
	private int appid;
	
	
	
	
	public int getConversionId() {
		return conversionId;
	}
	public void setConversionId(int requestId) {
		this.conversionId = requestId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getConversionUrl() {
		return conversionUrl;
	}
	public void setConversionUrl(String requestUrl) {
		this.conversionUrl = requestUrl;
	}
	public String getConversionTime() {
		return conversionTime;
	}
	public void setConversionTime(String requestTiime) {
		this.conversionTime = requestTiime;
	}

	public String getApplicationKey() {
		return applicationKey;
	}
	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	
	public String getConversionToTime() {
		return conversionToTime;
	}
	public void setConversionToTime(String conversionToTime) {
		this.conversionToTime = conversionToTime;
	}
	public Timestamp getConversionTimestamp() {
		return conversionTimestamp;
	}
	public void setConversionTimestamp(Timestamp conversionTimestamp) {
		this.conversionTimestamp = conversionTimestamp;
	}
	
	@Override
	public String toString() {
		return "ServerRequest [conversionId=" + conversionId + ", ip=" + ip + ", applicationKey=" + applicationKey
				+ ", conversionUrl=" + conversionUrl + ", conversionTime=" + conversionTime + ", conversionToTime="
				+ conversionToTime + ", conversionTimestamp=" + conversionTimestamp + ", partnerId=" + partnerId
				+ ", appid=" + appid + "]";
	}
	
	
	

	
	
	
}
