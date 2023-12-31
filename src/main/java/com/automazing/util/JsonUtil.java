package com.automazing.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	/**
	 * This method is used to covert a Object to JSON String
	 * @param obj
	 * @return
	 */
	public static String getSerializedJSON(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			System.out.println("JSON BODY PAYLOAD ====> "+ jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

}
