package com.requestTracker.exception;

public class ExceptionConstant {
	// EXCEPTIONS THROWN WHILE VALIDATING INPUT PARAMETERS
	public static String INVALID_APPLICATION_KEY			=	"Provide a correct key to the application..";
	public static String INVALID_HOST_ADDRESS				=	"Provide a correct Host ";
	public static String INVALID_REQUEST_URL				=	"Provide a correct request URL...";
	public static String INVALID_PARTNERID					= 	"No such partner exists";
	public static String INVALID_APPID						= 	"Please select an Active Partner";
	public static String HOST_BLACKLISTED					= 	"Host is blacklisted";
	public static String INSUFFICIENT_PARAMS				= 	"Please pass correct params.";
	// EXCEPTION FROM  SERVICE
	public static String NO_DATA_FOUND						=	"No data found";
	public static String REQUEST_NOT_RECORDED				= 	"The request was not recorded";
	public static String EMPTY_IP_LIST						= 	"No request was made ";
	public static String EMPTY_ACTIVE_PARTNER_LIST			= 	"No active parners found... ";
	public static String APP_NOT_ACTIVE_FOR_PARTNER			= 	"Application is not active for the partner";
	public static String NO_RESULT_FOUND					=	" No result found";
}
