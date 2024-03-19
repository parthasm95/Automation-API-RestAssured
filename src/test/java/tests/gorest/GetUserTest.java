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
	
}
