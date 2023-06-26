package com.automazing.util;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TokenUtil {
	
	public static String generateOAuthToken(String authUrl,Map<String, String> paramsMap, Map<String, String> headersMap, String responseTokenKeyName) {
		RequestSpecification request;
		Response response = null;
		request = RestAssured.given();
		if(paramsMap != null) {
			for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				
				request.formParam(key, value);
			}
		}
		
		if(headersMap!=null) {
			for(Map.Entry<String, String> entry : headersMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				
				request.header(key, value);
			}
		}
		
		response = request.post(authUrl);
		//String responseBody = response.getBody().asString();
		//JsonPath jsonPath = new JsonPath(responseBody);
		JsonPath jsonPath = response.jsonPath();

		return jsonPath.get(responseTokenKeyName).toString();
	}
}
