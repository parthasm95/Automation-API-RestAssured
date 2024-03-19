package tests.gorest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.constants.HttpStatus;
import com.automazing.pojo.User;
import com.automazing.util.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
import tests.base.BaseTest;

public class E2ETest extends BaseTest{
	
	private int userId;
	private User user;
	
	@BeforeMethod
	public void setUpProperties() {
		setUp("src/test/resources/properties/gorest.properties");
	}
	
	//Create a new User
	@Test
	public void createNewUserTest() {
		user = new User("Ivaan", "ivaan"+RandomUtil.gerenateInt()+"@gmail.com", "male", "active");
		Response response = restClient.post(Constants.GOREST_ENDPOINT, "json", user, true);
		response.then().log().all()
		.assertThat().statusCode(HttpStatus.CREATED_201.getCode());
		
		userId = response.jsonPath().get("id");
		System.out.println("User ID is : "+userId);
	}
	
	//get same User
	@Test
	public void getNewUserTest() {
		Response response = restClient.get(Constants.GOREST_ENDPOINT +"/"+userId, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode());
		String responseString = response.asString();
		Assert.assertEquals(user.getName(), JsonPath.read(responseString, "$.name"));
		Assert.assertEquals(user.getEmail(), JsonPath.read(responseString, "$.email"));
		Assert.assertEquals(user.getGender(), JsonPath.read(responseString, "$.gender"));
		Assert.assertEquals(user.getStatus(), JsonPath.read(responseString, "$.status"));
	}
	
	//update same user
	@Test
	public void updateNewUserTest() {
		
	}
	
	//delete same user
	@Test
	public void deleteNewUserTest() {
		
	}
	
	//try to get deleted user
	@Test
	public void getDeletedUserTest() {
		
	}
	
	

}
