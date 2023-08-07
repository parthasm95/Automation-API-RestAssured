package tests.gorest;

import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automazing.pojo.User;
import com.automazing.restclient.RestClient;
import com.automazing.util.ExcelUtil;
import com.automazing.util.HeaderUtil;
import com.automazing.util.PropertiesUtil;
import com.automazing.util.RandomUtil;

import io.restassured.response.Response;

public class CreateUserTest {
	
	private Properties prop;
	private PropertiesUtil propertiesUtil;
	
	private String baseURI;
	private String basePath;
	
	private Map<String,String> headers;
	
	@BeforeMethod
	public void setup() {
		System.out.println("loading properties...");
		propertiesUtil = new PropertiesUtil();
		String propFilePath = "src/test/resources/properties/gorest.properties";
		prop = propertiesUtil.loadProp(propFilePath);
		baseURI = prop.getProperty("baseURI");
		basePath = prop.getProperty("basePath");
		
		String headerFilePath = "src/test/resources/headers/gorest_headers.properties";
		headers = HeaderUtil.buildHeaders(headerFilePath);
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
		Response response  = RestClient.doPost(baseURI, basePath, headers, null, true, user);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
	@Test (dataProvider = "getUserData")
	public void createUserPostTestByExcel(String name, String gender, String status) {
		String randomEmail = name+RandomUtil.gerenateInt()+"@gmail.com";
		User user = new User(name, randomEmail, gender, status);
		Response response  = RestClient.doPost(baseURI, basePath, headers, null, true, user);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
	
}
