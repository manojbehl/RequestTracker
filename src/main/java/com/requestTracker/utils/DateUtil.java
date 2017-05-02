package com.requestTracker.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
    public static Timestamp	convertStringToDateFormat(String dateInString){
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
			Date parsedDate =  dateFormat.parse(dateInString);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
   

}
