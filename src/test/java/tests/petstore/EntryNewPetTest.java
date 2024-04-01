package tests.petstore;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automazing.constants.HttpStatus;
import com.automazing.pojo.Pet;
import com.automazing.pojo.Pet.Category;
import com.automazing.pojo.Pet.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import tests.base.BaseTest;

public class EntryNewPetTest extends BaseTest {
	
	@BeforeMethod
	public void setUpProperties() {
		setUp("petstore");
	}
	
	@Test
	public void entryNewPetTest() {
		Category category = new Category(10, "beagle");
		Tag tag1 = new Tag(20, "dog");
		Tag tag2 = new Tag(30, "mid size");
		
		Pet pet = new Pet(12345, category, "Tommy", Arrays.asList("pic1.com", "pic2.com"), Arrays.asList(tag1, tag2), "available");
		
		Response response = restClient.post("/pet", "json", pet, true);
		response.then().log().all().statusCode(HttpStatus.OK_200.getCode());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Pet petResponse = mapper.readValue(response.getBody().asString(), Pet.class);
			Assert.assertEquals(petResponse.getId(), pet.getId());
			Assert.assertEquals(petResponse.getCategory().getId(), pet.getCategory().getId());
			Assert.assertEquals(petResponse.getCategory().getName(), pet.getCategory().getName());
			Assert.assertEquals(petResponse.getName(), pet.getName());
			Assert.assertEquals(petResponse.getTags().get(0).getId(), pet.getTags().get(0).getId());
			Assert.assertEquals(petResponse.getTags().get(0).getName(), pet.getTags().get(0).getName());
			Assert.assertEquals(petResponse.getTags().get(1).getId(), pet.getTags().get(1).getId());
			Assert.assertEquals(petResponse.getTags().get(1).getName(), pet.getTags().get(1).getName());
			Assert.assertEquals(petResponse.getStatus(), pet.getStatus());
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
//	@Test
//	public void entryNewPet_With_Builder_Pattern_Test() {
//		
//		Pet pet = new Pet.PetBuilder().id(null).build();
//		
//		
//		Response response = restClient.post("/pet", "json", pet, true);
//		response.then().log().all().statusCode(HttpStatus.OK_200.getCode());
//		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			Pet petResponse = mapper.readValue(response.getBody().asString(), Pet.class);
//			Assert.assertEquals(petResponse.getId(), pet.getId());
//			Assert.assertEquals(petResponse.getCategory().getId(), pet.getCategory().getId());
//			Assert.assertEquals(petResponse.getCategory().getName(), pet.getCategory().getName());
//			Assert.assertEquals(petResponse.getName(), pet.getName());
//			Assert.assertEquals(petResponse.getTags().get(0).getId(), pet.getTags().get(0).getId());
//			Assert.assertEquals(petResponse.getTags().get(0).getName(), pet.getTags().get(0).getName());
//			Assert.assertEquals(petResponse.getTags().get(1).getId(), pet.getTags().get(1).getId());
//			Assert.assertEquals(petResponse.getTags().get(1).getName(), pet.getTags().get(1).getName());
//			Assert.assertEquals(petResponse.getStatus(), pet.getStatus());
//			
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		
//	}

}
