package tests;

import org.testng.annotations.Test;

import com.automazing.pojo.User;
import com.automazing.restclient.RestClient;

import io.restassured.response.Response;

public class CreateUserTest {
	
	String baseURI = "https://gorest.co.in";
	String basePath = "/public/v2/users";
	String token = "aba93e15695fdbb802f1598d4fdc67384453802dd2b264700d0bfb2443184a71";
	
	@Test
	public void createUserPostTest() {
		User user = new User("Ivaan", "ivaan@gmail.com", "male", "active");
		Response response  = RestClient.doPost("JSON", baseURI, basePath, token, null, true, user);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
}
