package com.requestTracker.utils;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenrator {
	private static final Random RANDOM = new SecureRandom();

	public static String generatePswd(int len) {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String password = "";
		for (int i = 0; i < len; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			password += letters.substring(index, index + 1);
		}
		return password;
	}

}
