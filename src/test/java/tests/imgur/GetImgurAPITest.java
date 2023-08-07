package tests.imgur;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.restclient.RestClient;
import com.automazing.util.HeaderUtil;
import com.automazing.util.PropertiesUtil;
import com.automazing.util.TokenUtil;

import io.restassured.response.Response;

public class GetImgurAPITest {
	
	private Properties prop;
	private PropertiesUtil propertiesUtil;
	private String token;
	private String authUrl;
	private String accessToken;
	private String baseURI;
	private String accountStatusCheckPath;
	private String accountUserName;
	
	private Map<String, String> headers;
	
	@BeforeMethod
	public void setup() {
		System.out.println("loading properties...");
		propertiesUtil = new PropertiesUtil();
		String propFilePath = "src/test/resources/properties/imgur.properties";
		prop = propertiesUtil.loadProp(propFilePath);
		
		//Construct Map for form parameters to get OAuth Token
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("refresh_token", prop.getProperty("refresh_token"));
		paramsMap.put("client_id", prop.getProperty("client_id"));
		paramsMap.put("client_secret", prop.getProperty("client_secret"));
		paramsMap.put("grant_type", prop.getProperty("grant_type"));
		
		//generate OAuth Token
		authUrl = prop.getProperty("authUrl");
		token = TokenUtil.generateOAuthToken(authUrl, paramsMap, null, "access_token");
		accessToken = "Bearer "+ token;
		System.out.println("Generated Access Token is : "+accessToken);
		
		baseURI = prop.getProperty("baseURI");
		accountStatusCheckPath = prop.getProperty("accountStatusCheckPath");
		accountUserName = prop.getProperty("accountUserName");
	}
	
	@Test
	public void getAccountBlockStatusTest() {
		String headerFilePath = "src/test/resources/headers/imgur_headers.properties";
		headers = HeaderUtil.buildHeaders(headerFilePath);
		headers.put("Authorization", accessToken);
		
		Response response = RestClient.doGet(baseURI, accountStatusCheckPath +accountUserName + "/block", headers, null, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
}
