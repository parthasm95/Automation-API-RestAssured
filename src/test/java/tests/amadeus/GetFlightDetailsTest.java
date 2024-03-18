package tests.amadeus;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.constants.HttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import tests.base.BaseTest;

public class GetFlightDetailsTest extends BaseTest {
	
	@BeforeMethod
	public void setUpProperties(){
		setUp("src/test/resources/properties/amadeus.properties");
	}
	
	
	@Test(priority = 1)
	public void getFlightInfoTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("origin", "PAR");
		params.put("maxPrice", 200);
		Response response = restClient.get(Constants.AMADEUS_FLIGHTBOOKING_ENDPOINT, null, params, false);
		response.then().log().all()
			.assertThat().statusCode(HttpStatus.OK_200.getCode())
			.and().extract().response();
		
		JsonPath jsonPath = response.jsonPath();
		String type = jsonPath.get("data[0].type");
		System.out.println("Type is : "+type);
	}
	
}
