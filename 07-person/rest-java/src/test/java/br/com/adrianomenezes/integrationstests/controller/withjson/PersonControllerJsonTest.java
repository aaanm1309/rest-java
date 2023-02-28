package br.com.adrianomenezes.integrationstests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.adrianomenezes.configs.TestConfig;
import br.com.adrianomenezes.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.adrianomenezes.integrationstests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonVO personVO;
	
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		personVO = new PersonVO();
		
		
	}
	
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfig.CONTENT_TYPE_JSON)
							.body(personVO)
						.when()
							.post()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();
		
		PersonVO createdPerson =  objectMapper.readValue(content, PersonVO.class);
		personVO = createdPerson;
		
		assertNotNull(createdPerson);
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("First name test", createdPerson.getFirstName());
		assertEquals("Last name test", createdPerson.getLastName());
		assertEquals("NY - EUA", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
}

	@Test
	@Order(2)
	public void testCreateWithWorngOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO_ERRO)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfig.CONTENT_TYPE_JSON)
							.body(personVO)
						.when()
							.post()
						.then()
							.statusCode(403)
						.extract()
							.body()
								.asString();
	
		
		assertNotNull(content);
//		System.out.println(content);

		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfig.CONTENT_TYPE_JSON)
							.pathParam("id", personVO.getId())
						.when()
							.get("{id}")
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();
		
		PersonVO receivedPerson =  objectMapper.readValue(content, PersonVO.class);
		
		assertNotNull(receivedPerson);
		assertNotNull(receivedPerson.getId());
		assertNotNull(receivedPerson.getFirstName());
		assertNotNull(receivedPerson.getLastName());
		assertNotNull(receivedPerson.getAddress());
		assertNotNull(receivedPerson.getGender());
		assertTrue(receivedPerson.getId() > 0);
		
		assertEquals("First name test", receivedPerson.getFirstName());
		assertEquals("Last name test", receivedPerson.getLastName());
		assertEquals("NY - EUA", receivedPerson.getAddress());
		assertEquals("Male", receivedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
							.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO_ERRO)
							.setBasePath("/api/person/v1")
							.setPort(TestConfig.SERVER_PORT)
							.addFilter(new RequestLoggingFilter(LogDetail.ALL))
							.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfig.CONTENT_TYPE_JSON)
							.pathParam("id", personVO.getId())
						.when()
							.get("{id}")
						.then()
							.statusCode(403)
						.extract()
							.body()
								.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
		
	}

	private void mockPerson() {
		personVO.setFirstName("First name test");
		personVO.setLastName("Last name test");
		personVO.setAddress("NY - EUA");
		personVO.setGender("Male");
	}

}
