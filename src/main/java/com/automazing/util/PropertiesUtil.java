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
	public Properties loadProp(String propFilePath) {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(propFilePath);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
