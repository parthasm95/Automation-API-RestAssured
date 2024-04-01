package com.automazing.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
private Properties prop;
	
	/**
	 * This method is used to load the properties from properties file
	 * @return
	 */
	public Properties loadProp(String appName) {
		String envName = System.getProperty("env");
		String propFilePath = "";
		if(envName == null) {
			System.out.println("No Env Passed, Running Tests on QA Env...");
			propFilePath = "src/test/resources/properties/"+appName.toLowerCase()+"/qa/"+appName.toLowerCase()+".properties";
		}else {
			propFilePath = "src/test/resources/properties/"+appName.toLowerCase()+"/"+envName.toLowerCase()+"/"+appName.toLowerCase()+".properties";
		}
		
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(propFilePath);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("[Error]Please pass correct Env name...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
