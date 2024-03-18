package tests.gorest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.constants.HttpStatus;

import io.restassured.response.Response;
import tests.base.BaseTest;


public class GetUserTest extends BaseTest{
	
	@BeforeMethod
	public void setUpProperties() {
		setUp("src/test/resources/properties/gorest.properties");
	}
	
	
	@Test(priority = 1)
	public void getAllUserListAPITest() {
		Response response = restClient.get(Constants.GOREST_ENDPOINT, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode());
	}
	
	@Test(priority = 2)
	public void getSpecificUserAPITest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gender", "male");
		params.put("status", "active");
		Response response = restClient.get(Constants.GOREST_ENDPOINT, null, params, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode());
	}
	
	
//	private Map<String,String> headers;
	
//	@BeforeMethod
//	public void setup() {
//		System.out.println("loading properties...");
//		propertiesUtil = new PropertiesUtil();
//		String propFilePath = "src/test/resources/properties/gorest.properties";
//		prop = propertiesUtil.loadProp(propFilePath);
//		baseURL = prop.getProperty("baseURI");
//		basePath = prop.getProperty("basePath");
//		
////		String headerFilePath = "src/test/resources/headers/gorest_headers.properties";
////		headers = HeaderUtil.buildHeaders(headerFilePath);
//		url = baseURL + basePath + "/users";
//	}
	
//	@Test(priority = 1)
//	public void getAllUserListAPITest() {
//		Response response = RestClient.doGet(baseURI, basePath, headers, null, true);
//		System.out.println(response.statusCode());
//		System.out.println(response.prettyPrint());
//	}
//	
//	
//	@Test(priority = 2)
//	public void getSpecificUserAPITest() {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("name", "Heema Bandopadhyay");
//		params.put("gender", "male");
//		Response response = RestClient.doGet(baseURI, basePath, headers, params, true);
//		System.out.println(response.statusCode());
//		System.out.println(response.prettyPrint());
//	}
}
