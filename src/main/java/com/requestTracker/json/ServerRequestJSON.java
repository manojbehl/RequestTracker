package com.requestTracker.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ServerRequestJSON {
	
	private int requestId;
	private String ip;
	private String applicationKey;
	private String requestUrl;
	private String requestTime;
	
	
	
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getApplicationKey() {
		return applicationKey;
	}
	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}



	@Override
	public String toString() {
		return "ServerRequestJSON [requestId=" + requestId + ", ip=" + ip + ", applicationKey=" + applicationKey
				+ ", requestUrl=" + requestUrl + ", requestTime=" + requestTime + "]";
	}
	
	
	

}
