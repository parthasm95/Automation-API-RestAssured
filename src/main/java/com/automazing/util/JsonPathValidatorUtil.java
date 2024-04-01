package com.automazing.util;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import io.restassured.response.Response;

public class JsonPathValidatorUtil {

	public <T> T read(Response response, String jsonPath) {

		String jsonResponse = response.getBody().asString();
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> readList(Response response, String jsonPath) {

		String jsonResponse = response.getBody().asString();
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {

		String jsonResponse = response.getBody().asString();
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
