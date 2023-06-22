package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.automazing.restclient.RestClient;

import io.restassured.response.Response;

public class GetUserTest {
	
	String baseURI = "https://gorest.co.in";
	String basePath = "/public/v2/users";
	String token = "aba93e15695fdbb802f1598d4fdc67384453802dd2b264700d0bfb2443184a71";
	
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
