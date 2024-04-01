package tests.fakestore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.Constants;
import com.automazing.pojo.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import tests.base.BaseTest;

public class GetProductsTest extends BaseTest{
	
	@BeforeMethod
	public void setUpProperties() {
		setUp("fakestore");
	}
	
	
	@Test(priority = 1)
	public void getAllProductListAPITest() {
		Response response = restClient.get(Constants.FAKESTORE_ENDPOINT, true);
		response.then().log().all();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Product product[] = mapper.readValue(response.getBody().asString(), Product[].class);
			for(Product p: product) {
				System.out.println("Id : "+p.getId());
				System.out.println("Title : "+p.getTitle());
				System.out.println("Price : "+p.getPrice());
				System.out.println("Description : "+p.getDescription());
				System.out.println("Category : "+p.getCategory());
				System.out.println("Image : "+p.getImage());
				System.out.println("Rate : "+p.getRating().getRate());
				System.out.println("Count : "+p.getRating().getCount());
				System.out.println("--------------------------------");
				
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

}
