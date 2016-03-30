package com.diskoverorta.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationUtils {	
	
	public static String getNLPPackageChoice(){
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;
		try {
	        input = ConfigurationUtils.class.getClassLoader().getResourceAsStream("config.properties");						
			prop.load(input);
			value= prop.getProperty("NLPPackageChoice");
		} catch (Exception ex) {
			ex.printStackTrace();			
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
		return value;		 
	}
}
