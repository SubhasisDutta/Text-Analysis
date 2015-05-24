package com.diskoverorta.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationUtils {
	public static String getNLPPackageChoice(){
		Properties prop = new Properties();
		InputStream input = null;
		String value = null;
		try {			 
			input = new FileInputStream("config.properties");	 
			// load a properties file
			prop.load(input);	 
			// get the property value and print it out
			value= prop.getProperty("NLPPackageChoice");
		} catch (IOException ex) {
			ex.printStackTrace();			
		} finally {
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
