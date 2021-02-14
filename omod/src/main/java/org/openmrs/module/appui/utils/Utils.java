package org.openmrs.module.appui.utils;

import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public  class Utils {
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
	}
	
	public static String capitalize(String str) {
	    if(str == null || str.isEmpty()) {
	        return str;
	    }
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static boolean isNonEmptyString(String str) {
	  return (str != null && !str.trim().isEmpty());
	}
	
	/**
	 * Validates form inputs.
	 * 
	 * @param formInputs
	 * @return
	 */
	public static StringBuilder validateFormInputs(
			Map<String, String> formInputs, String[] requiredFormFields) {
		System.out.println("vfi 10.");
		StringBuilder errors = new StringBuilder();
		for (String key: requiredFormFields) {
			//String value = formInputs.get(key).replaceAll("\\s","");
			String value = formInputs.get(key);
			System.out.println("vfi 20 Checking value for key: " + key + ", value: \"" + value + "\"");
			System.out.println("vfi 25 value == \"\" = " + (value == "") + ", value==null = " + (value==null));
			if (!isNonEmptyString(value)) {
				System.out.println("vfi 30: key " + key + " has empty value.");
				errors.append(capitalize(key) + " is required.");
			} else {
				System.out.println("vfi 40: key " + key + " has a non empty value of ****" + value + "====");
			}
			if (key == "email" && !isValidEmailAddress(value) ) {
				System.out.println("vfi 50: Invalid email address.");
				errors.append(value + " is not a valid email address.");
			}
		}
		System.out.println("vfi 60 errors are " + errors);
		return errors;
	}
}
