package com.automazing.restclient;

import java.io.File;
import java.util.Map;

import com.automazing.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This Class contains all HTTP methods which can call APIs and different
 * generic methods to get response and fetch values from response
 * 
 * @author Partha Mohapatra
 *
 */

public class RestClient {

	public static Response doGet(String contentType, String baseURI, String basePath, String token,
			Map<String, String> paramsMap, boolean log) {

		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, token, paramsMap, log);
			return getResponse("GET", request, basePath);
		}
		return null;
	}

	public static Response doPost(String contentType, String baseURI, String basePath, String token,
			Map<String, String> paramsMap, boolean log, Object payLoadObj) {

		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(contentType, token, paramsMap, log);
			addRequestPayload(request, payLoadObj);
			return getResponse("POST", request, basePath);
		}
		return null;
	}

	public static void doPut() {

	}

	public static void doDelete() {

	}

	public static void addRequestPayload(RequestSpecification request, Object payLoadObj) {
			String jsonPayload = TestUtil.getSerializedJSON(payLoadObj);
			request.body(jsonPayload);
	}

	private static boolean setBaseURI(String baseURI) {

		if (baseURI == null || baseURI.isEmpty()) {
			System.out.println("Please pass the correct base URI....either it is null or blank/empty...");
			return false;
		}
		try {
			RestAssured.baseURI = baseURI;
			return true;
		} catch (Exception e) {
			System.out.println("some exception got occurred while assiging the base URI with Rest Assured....");
			return false;
		}
	}

	private static RequestSpecification createRequest(String contentType, String token, Map<String, String> paramsMap,
			boolean log) {
		RequestSpecification request;
		if (log) {
			request = RestAssured.given().log().all();
		} else {
			request = RestAssured.given();
		}

		request.header("Authorization", "Bearer " + token);

		if (!(paramsMap == null)) {
			request.queryParams(paramsMap);
		}

		if (contentType != null) {
			if (contentType.equalsIgnoreCase("JSON")) {
				request.contentType(ContentType.JSON);
			} else if (contentType.equalsIgnoreCase("XML")) {
				request.contentType(ContentType.XML);
			} else if (contentType.equalsIgnoreCase("TEXT")) {
				request.contentType(ContentType.TEXT);
			} else if (contentType.equalsIgnoreCase("multipart")) {
				request.multiPart("image", new File(""));
			}
		}

		return request;
	}

	private static Response getResponse(String httpMethod, RequestSpecification request, String basePath) {
		return executeAPI(httpMethod, request, basePath);
	}

	private static Response executeAPI(String httpMethod, RequestSpecification request, String basePath) {
		Response response = null;
		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;
		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;

		default:
			System.out.println("Please pass the corrent http method....");
			break;
		}

		return response;
	}

}
