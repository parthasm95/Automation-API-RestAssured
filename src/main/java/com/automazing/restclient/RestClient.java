package com.automazing.restclient;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.util.SystemOutLogger;
import org.checkerframework.checker.units.qual.s;

import com.automazing.util.JsonUtil;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.When;

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
	////////////////////////
	// Code Refactoring//
	///////////////////////

	private RequestSpecBuilder specBuilder;
	private Properties prop;
	private String baseURI;

	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}

	// crud public methods
	public Response get(String serviceUrl, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec()).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().get(serviceUrl);
	}

	public Response get(String serviceUrl, Map<String, String> headersMap, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headersMap)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap)).when().get(serviceUrl);

	}

	public Response get(String serviceUrl, Map<String, String> headersMap, Map<String, Object> queryParams,
			boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParams)).log().all().when().get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, queryParams)).when().get(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object payload, Boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType)).log().all().when().post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(payload, contentType)).when().post(serviceUrl);
	}

	public Response post(String serviceUrl, String contentType, Object payload, Map<String, String> headersMap,
			boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).log().all().when()
					.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).when().post(serviceUrl);

	}

	public Response put(String serviceUrl, String contentType, Object payload, Boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType)).log().all().when().put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().put(serviceUrl);
	}

	public Response put(String serviceUrl, String contentType, Object payload, Map<String, String> headersMap,
			boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).log().all().when()
					.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).when().put(serviceUrl);

	}

	public Response patch(String serviceUrl, String contentType, Object payload, Boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType)).log().all().when().patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().patch(serviceUrl);
	}

	public Response patch(String serviceUrl, String contentType, Object payload, Map<String, String> headersMap,
			boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).log().all().when()
					.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(payload, contentType, headersMap)).when().patch(serviceUrl);

	}

	public Response delete(String serviceUrl, boolean log) {
		if (log) {
			return RestAssured.given(createRequestSpec()).log().all().when().delete(serviceUrl);
		}
		return RestAssured.given(createRequestSpec()).when().delete(serviceUrl);

	}
	
	
	//private supported methods
	
	private RequestSpecification createRequestSpec() {
		specBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap) {
		specBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams) {
		specBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object payload, String contentType) {
		specBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		setRequestContentType(contentType);
		if (payload != null) {
			specBuilder.setBody(payload);
		}
		return specBuilder.build();
	}

	private RequestSpecification createRequestSpec(Object payload, String contentType, Map<String, String> headersMap) {
		specBuilder.setBaseUri(baseURI);
		addAuthorizationHeader();
		setRequestContentType(contentType);
		if (payload != null) {
			specBuilder.setBody(payload);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
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

	private void addAuthorizationHeader() {
		System.out.println("Authenthication Type is : " + prop.getProperty("authType"));

		if (prop.getProperty("authType").equals("OAuth2")) {
			if (prop.getProperty("grantType") != null && prop.getProperty("clientId") != null
					&& prop.getProperty("clientSecret") != null && prop.getProperty("oAuthTokenURL") != null) {
				System.out.println("Constucting Autherization header with OAuth 2.0 Token...");
				specBuilder.addHeader("Authorization", "Bearer " + getOAuth2AccessToken());
				return;
			}
			System.out.println("[Error]Please check Auth 2.0 properties...");

		} else if (prop.getProperty("authType").equals("Bearer") && prop.getProperty("authToken") != null) {
			System.out.println("Constucting Autherization header with Bearer Token...");
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("authToken"));
			return;

		} if (prop.getProperty("authType").equals("JWT")) {
			if (prop.getProperty("username") != null && prop.getProperty("password") != null 
					&& prop.getProperty("jwtTokenUrl") != null) {
				System.out.println("Constucting Autherization header with JWT Token...");
				specBuilder.addHeader("Authorization", "Bearer " + getJwtToken());
				return;
			}
			System.out.println("[Error]Please check Auth 2.0 properties...");

		} else if (prop.getProperty("authType").equals("None")) {
			return;

		} else {
			System.out.println("Filed to add Autherizaion header. Please check property file.");
		}
	}
	
	private String getJwtToken() {
		String jwtTokenString = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body("TBU")
				.when()
					.post(prop.getProperty("jwtTokenUrl"))
				.then()
					.extract().path("token");
		return jwtTokenString;
	}

	private String getOAuth2AccessToken() {
		String accessToken = RestAssured.given()
				.contentType(ContentType.URLENC)
				.formParam("grant_type", prop.getProperty("grantType"))
				.formParam("client_id", prop.getProperty("clientId"))
				.formParam("client_secret", prop.getProperty("clientSecret")).when()
				.post(prop.getProperty("oAuthTokenURL")).then().assertThat().statusCode(200).extract()
				.path("access_token");
		System.out.println("OAuth 2.0 Access Token is : " + accessToken);

		return accessToken;
	}


	/////////////////////////////////////////////////////////////////////////////
	// OLD Code//
	////////////////////////////////////////////////////////////////////////////
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

		if (payLoadObj instanceof Map) {
			Map<String, String> formMap = (Map<String, String>) payLoadObj;
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

		if (payLoadObj instanceof Map) {
			Map<String, String> formMap = (Map<String, String>) payLoadObj;
			formMap.remove("filePath");
			request.formParams(formMap);
		} else {
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
		if (!(headers == null)) {
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

}
