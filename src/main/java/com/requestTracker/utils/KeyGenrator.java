package com.requestTracker.utils;

import java.io.UnsupportedEncodingException;

import org.springframework.util.Base64Utils;

public class KeyGenrator {
	public static String partnerAppKeyGenerator(int paernerId, int appId) {
        String partnerAppKey = null;
        String stringToEnccrypt = "" + paernerId + appId;
        try {
            // Encode using basic encoder LAYER =1
            String base64encodedString = Base64Utils.encodeToString(stringToEnccrypt.getBytes("utf-8"));
            //System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);
            // Encode again LAYER =2
            base64encodedString = Base64Utils.encodeToString(base64encodedString.getBytes("utf-8"));
           // System.out.println("Dual encryption Key : " + base64encodedString);
            partnerAppKey = base64encodedString;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }
        return partnerAppKey;
    }

}
