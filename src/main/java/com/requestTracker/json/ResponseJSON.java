package com.requestTracker.json;

public class ResponseJSON {
	private boolean error;
	private String message;
	private Object result;
	
	public boolean isError() {
		return error;
	}
	public String getMessage() {
		return message;
	}
	public Object getResult() {
		return result;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ResponseJSON [error=" + error + ", message=" + message + ", result=" + result + "]";
	}
	
	
}
