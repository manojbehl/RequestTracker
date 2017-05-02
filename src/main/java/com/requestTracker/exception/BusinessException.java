package com.requestTracker.exception;


public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;
	private int exceptionType;
	private String exceptionMessage;
	

	
	//Custom Business Exceptions
	public BusinessException(String message, int exceptionType){
		super(message);
		this.setExceptionType(exceptionType);
	}

	public BusinessException(int exceptionType){
		this.setExceptionType(exceptionType);
	}
	
	public BusinessException( String exceptionMessage){
		super(exceptionMessage);
		this.setExceptionMessage(exceptionMessage);
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}

	

}
