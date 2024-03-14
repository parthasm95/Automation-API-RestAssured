package com.automazing.restclient;

import java.io.File;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;

import com.automazing.util.JsonUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This Class contains all HTTP methods which can call APIs and different
 * generic methods to get response and fetch values from response
 * 
 * @author Partha
 *
 */

public class RestClient {
	private static String uploadFilePath;

	public static Response doGet(String baseURI, String basePath, Map<String, String> headers,
			Map<String, String> queryParamsMap, boolean log) {
		
		
		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(headers, queryParamsMap, log);
			return getResponse("GET", request, basePath);
		}
		return null;
	}

	public static Response doPost(String baseURI, String basePath, Map<String, String> headers,
			Map<String, String> queryParamsMap, boolean log, Object payLoadObj) {
		
		if(payLoadObj instanceof Map) {
			Map<String, String> formMap = (Map<String, String>)payLoadObj;
			uploadFilePath = formMap.get("filePath");
		}

		if (setBaseURI(baseURI)) {
			RequestSpecification request = createRequest(headers, queryParamsMap, log);
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
		
		if(payLoadObj instanceof Map) {
			Map<String, String> formMap = (Map<String, String>)payLoadObj;
			formMap.remove("filePath");
			request.formParams(formMap);
		}else {
			String jsonPayload = JsonUtil.getSerializedJSON(payLoadObj);
			request.body(jsonPayload);
		}
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

	private static RequestSpecification createRequest(Map<String, String> headers, Map<String, String> queryParamsMap,
			boolean log) {
		RequestSpecification request;
		if (log) {
			request = RestAssured.given().log().all();
		} else {
			request = RestAssured.given();
		}
		
		String contentType = headers.get("Content-Type");
		
		headers.remove("Content-Type");
		if(!(headers == null)) {
			request.headers(headers);
		}
		

		if (!(queryParamsMap == null)) {
			request.queryParams(queryParamsMap);
		}

		if (contentType != null) {
			if (contentType.equalsIgnoreCase("JSON")) {
				request.contentType(ContentType.JSON);
			} else if (contentType.equalsIgnoreCase("XML")) {
				request.contentType(ContentType.XML);
			} else if (contentType.equalsIgnoreCase("TEXT")) {
				request.contentType(ContentType.TEXT);
			} else if (contentType.equalsIgnoreCase("multipart")) {
				request.multiPart("image", new File(uploadFilePath));
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
	/////////////////////
	//Code Refactoring//
	///////////////////
	
	private static RequestSpecBuilder specBuilder;
	
	static {
		specBuilder = new RequestSpecBuilder();
	}
	
	public void addAuthorizationHeader() {
		specBuilder.addHeader("Authorization", "Bearer ");
	}
	
	private void setRequestContentType(String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;

		default:
			System.out.println("Please provide correct content Type");
			break;
		}
	}
	
	public RequestSpecification createRequestSpec() {
		specBuilder.setBaseUri("");
		addAuthorizationHeader();
		return specBuilder.build();
	}
	
	public RequestSpecification createRequestSpec(Map<String, String> headersMap) {
		specBuilder.setBaseUri("");
		addAuthorizationHeader();
		if(headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}
	
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryParams) {
		specBuilder.setBaseUri("");
		addAuthorizationHeader();
		if(headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();
	}
	
	public RequestSpecification createRequestSpec(Object payload, String contentType) {
		specBuilder.setBaseUri("");
		addAuthorizationHeader();
		
		return specBuilder.build();
	}
	
	

}
