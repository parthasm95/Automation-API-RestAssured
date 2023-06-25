package tests.gorest;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.restclient.RestClient;
import com.automazing.util.PropertiesUtil;

import io.restassured.response.Response;

public class GetUserTest {
	
	private Properties prop;
	private PropertiesUtil propertiesUtil;
	
	private String baseURI;
	private String basePath;
	private String token;
	
	@BeforeMethod
	public void setup() {
		System.out.println("loading properties...");
		propertiesUtil = new PropertiesUtil();
		String propFilePath = "src\\test\\resources\\properties\\gorest.properties";
		prop = propertiesUtil.loadProp(propFilePath);
		baseURI = prop.getProperty("baseURI");
		basePath = prop.getProperty("basePath");
		token = prop.getProperty("token");
	}
	
	@Test(priority = 1)
	public void getAllUserListAPITest() {
		Response response = RestClient.doGet("JSON", baseURI, basePath, token, null, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	
	@Test(priority = 2)
	public void getSpecificUserAPITest() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "Mohan Jha");
		params.put("gender", "male");
		Response response = RestClient.doGet("JSON", baseURI, basePath, token, params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
}
