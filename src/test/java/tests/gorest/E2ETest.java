package tests.gorest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.constants.HttpStatus;
import com.automazing.pojo.User;
import com.automazing.util.RandomUtil;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
import tests.base.BaseTest;

public class E2ETest extends BaseTest{
	
	private Integer userId;
	private User user;
	
	@BeforeMethod
	public void setUpProperties() {
		setUp("gorest");
	}
	
	//Create a new User
	@Test(priority = 1)
	public void createNewUserTest() {
		user = new User("Ivaan", "ivaan"+RandomUtil.gerenateInt()+"@gmail.com", "male", "active");
		Response response = restClient.post(Constants.GOREST_ENDPOINT, "json", user, true);
		response.then().log().all()
		.assertThat().statusCode(HttpStatus.CREATED_201.getCode());
		
		userId = response.jsonPath().get("id");
		System.out.println("User ID is : "+userId);
	}
	
//	@Test
//	public void createNewUserTest_With_Builder_Pattern() {
//
//		
//		User user1 = new User.UserBuilder()
//				.name(null)
//				.email(null)
//				.gender(null)
//				.status(null)
//				.build();
//		
//		Response response = restClient.post(Constants.GOREST_ENDPOINT, "json", user, true);
//		response.then().log().all()
//		.assertThat().statusCode(HttpStatus.CREATED_201.getCode());
//		
//		userId = response.jsonPath().get("id");
//		System.out.println("User ID is : "+userId);
//	}
	
	//get same User
	@Test(priority = 2)
	public void getNewUserTest() {
		Response response = restClient.get(Constants.GOREST_ENDPOINT +"/"+userId, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode());
		String responseString = response.asString();
		Assert.assertEquals(JsonPath.read(responseString, "$.id"), userId);
		Assert.assertEquals(JsonPath.read(responseString, "$.name"), user.getName());
		Assert.assertEquals(JsonPath.read(responseString, "$.email"), user.getEmail());
		Assert.assertEquals(JsonPath.read(responseString, "$.gender"), user.getGender());
		Assert.assertEquals(JsonPath.read(responseString, "$.status"), user.getStatus());
	}
	
	//update same user
	
	@Test(priority = 3)
	public void updateNewUserTest() {
		user.setStatus("inactive");
		Response response = restClient.put(Constants.GOREST_ENDPOINT +"/"+userId, "json", user, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode());
		String responseString = response.asString();
		Assert.assertEquals(user.getName(), JsonPath.read(responseString, "$.name"));
		Assert.assertEquals(user.getEmail(), JsonPath.read(responseString, "$.email"));
		Assert.assertEquals(user.getGender(), JsonPath.read(responseString, "$.gender"));
		Assert.assertEquals(user.getStatus(), JsonPath.read(responseString, "$.status"));
	}
	
	//delete same user
	@Test(priority = 4)
	public void deleteNewUserTest() {
		Response response = restClient.delete(Constants.GOREST_ENDPOINT+"/"+userId, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.NO_CONTENT_204.getCode());
	}
	
	//try to get deleted user
	@Test(priority = 5)
	public void getDeletedUserTest() {
		Response response = restClient.get(Constants.GOREST_ENDPOINT +"/"+userId, true);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.NOT_FOUND_404.getCode())
			.assertThat().statusLine(HttpStatus.NOT_FOUND_404.getmessage());
		Assert.assertEquals(response.jsonPath().get("message"), "Resource not found");
		
	}
	
}
