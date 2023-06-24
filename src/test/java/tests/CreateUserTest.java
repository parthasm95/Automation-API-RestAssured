package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automazing.pojo.User;
import com.automazing.restclient.RestClient;
import com.automazing.util.ExcelUtil;
import com.automazing.util.RandomUtil;

import io.restassured.response.Response;

public class CreateUserTest {
	
	String baseURI = "https://gorest.co.in";
	String basePath = "/public/v2/users";
	String token = "aba93e15695fdbb802f1598d4fdc67384453802dd2b264700d0bfb2443184a71";
	
	@DataProvider
	public Object[][] getUserData(){
		String filePath = "src\\test\\resources\\testdata\\GoRestUserTestData.xlsx";
		Object[][] userData = ExcelUtil.getTestData(filePath, "userdata");
		return userData;
	}
	
	@Test
	public void createUserPostTest() {
		User user = new User("Ivaan", "ivaan"+RandomUtil.gerenateInt()+"@gmail.com", "male", "active");
		Response response  = RestClient.doPost("JSON", baseURI, basePath, token, null, true, user);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	@Test (dataProvider = "getUserData")
	public void createUserPostTestByExcel(String name, String gender, String status) {
		String randomEmail = name+RandomUtil.gerenateInt()+"@gmail.com";
		User user = new User(name, randomEmail, gender, status);
		Response response  = RestClient.doPost("JSON", baseURI, basePath, token, null, true, user);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	
}
