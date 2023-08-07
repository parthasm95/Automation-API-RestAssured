package tests.imgur;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.restclient.RestClient;
import com.automazing.util.HeaderUtil;
import com.automazing.util.PropertiesUtil;

import io.restassured.response.Response;

public class PostImgurAPITest {
	
	private Properties prop;
	private PropertiesUtil propertiesUtil;
	private String baseURI;
	private String imageUploadPath;
	private Map<String, String> headers;
	
	@BeforeMethod
	public void setup() {
		System.out.println("loading properties...");
		propertiesUtil = new PropertiesUtil();
		String propFilePath = "src/test/resources/properties/imgur.properties";
		prop = propertiesUtil.loadProp(propFilePath);
		
		baseURI = prop.getProperty("baseURI");
		imageUploadPath = prop.getProperty("imageUploadPath");
		
	}
	
	@Test
	public void uploadImageTest() {
		String headerFilePath = "src/test/resources/headers/imgur_media_upload_headers.properties";
		headers = HeaderUtil.buildHeaders(headerFilePath);
		
		Map<String, String> formMap = new HashMap<String, String>();
		formMap.put("title", "my image");
		formMap.put("description", "This is test image");
		formMap.put("filePath", "./src/test/resources/testdata/test_image.png");
		
		Response response = RestClient.doPost(baseURI, imageUploadPath, headers, null, true, formMap);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
	}
}
