package tests.base;

import java.util.Properties;

import com.automazing.restclient.RestClient;
import com.automazing.util.PropertiesUtil;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	
	protected Properties prop;
	protected PropertiesUtil propertiesUtil;
	protected RestClient restClient;
	
	private String baseURL;
	private String basePath;
	
	public void setUp(String appName) {
		
		RestAssured.filters(new AllureRestAssured());
		
		System.out.println("loading properties...");
		propertiesUtil = new PropertiesUtil();
		prop = propertiesUtil.loadProp(appName);
		
		baseURL = prop.getProperty("baseURL");
		basePath = prop.getProperty("basePath");
		
		String baseURI = baseURL + basePath;
		restClient = new RestClient(prop, baseURI);
	}

}
