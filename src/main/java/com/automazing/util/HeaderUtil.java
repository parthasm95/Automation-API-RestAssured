package com.automazing.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HeaderUtil {
	public static Map<String, String> buildHeaders(String filePath) {
        Map<String, String> headers = new HashMap<>();
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                headers.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headers;
	}
}
