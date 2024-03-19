package tests.gorest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.constants.HttpStatus;
import com.automazing.pojo.User;
import com.automazing.util.ExcelUtil;
import com.automazing.util.RandomUtil;

import io.restassured.response.Response;
import tests.base.BaseTest;

public class CreateUserTest extends BaseTest{

	
	@BeforeMethod
	public void setUpProperties() {
		setUp("src/test/resources/properties/gorest.properties");
	}
	
	
	@DataProvider
	public Object[][] getUserData(){
		String filePath = "src/test/resources/testdata/GoRestUserTestData.xlsx";
		Object[][] userData = ExcelUtil.getTestData(filePath, "userdata");
		return userData;
	}
	
	@Test
	public void createUserPostTest() {
		User user = new User("Ivaan", "ivaan"+RandomUtil.gerenateInt()+"@gmail.com", "male", "active");
		Response response = restClient.post(Constants.GOREST_ENDPOINT, "json", user, true);
		response.then().log().all()
		.assertThat().statusCode(HttpStatus.CREATED_201.getCode());
	}
	
	@Test (dataProvider = "getUserData")
	public void createUserPostTestByExcel(String name, String gender, String status) {
		String randomEmail = name+RandomUtil.gerenateInt()+"@gmail.com";
		User user = new User(name, randomEmail, gender, status);
		Response response  = restClient.post(Constants.GOREST_ENDPOINT, "json", user, true);
		response.then().log().all()
		.assertThat().statusCode(HttpStatus.CREATED_201.getCode());
	}
	
}
