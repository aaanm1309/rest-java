package br.com.adrianomenezes.integrationstests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.adrianomenezes.configs.TestConfig;
import br.com.adrianomenezes.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.adrianomenezes.integrationstests.vo.AccountCredentialsVO;
import br.com.adrianomenezes.integrationstests.vo.PersonVO;
import br.com.adrianomenezes.integrationstests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
	private static PersonVO personVO;
	private static String accessTokenBearer;
	
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		personVO = new PersonVO();
		
		
	}
	
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("adriano", "admin123");
		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
//				.accept(TestConfig.CONTENT_TYPE_XML)
				.contentType(TestConfig.CONTENT_TYPE_XML)
					.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
					.getAccessToken();
		
		accessTokenBearer = "Bearer " + accessToken;

		specification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfig.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		assertNotNull(accessToken);
		assertNotNull(accessTokenBearer);
		System.out.println(accessTokenBearer);

	}
	
	/*
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
//				.accept(TestConfig.CONTENT_TYPE_XML)
				.contentType(TestConfig.CONTENT_TYPE_XML)
//					.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO)
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
		assertTrue(createdPerson.getEnabled());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("First name test", createdPerson.getFirstName());
		assertEquals("Last name test", createdPerson.getLastName());
		assertEquals("NY - EUA", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}
	*/
	
	@Test
	@Order(2)
	public void testCreateWithWorngOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		var content = given()
					.spec(specification)
					.contentType(TestConfig.CONTENT_TYPE_XML)
//					.accept(TestConfig.CONTENT_TYPE_XML)
					.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO_ERRO)
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
	
	/*
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
					.spec(specification)
					.contentType(TestConfig.CONTENT_TYPE_XML)
//					.accept(TestConfig.CONTENT_TYPE_XML)
//					.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO)
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
		assertTrue(receivedPerson.getEnabled());
		assertTrue(receivedPerson.getId() > 0);
		
		assertEquals("First name test", receivedPerson.getFirstName());
		assertEquals("Last name test", receivedPerson.getLastName());
		assertEquals("NY - EUA", receivedPerson.getAddress());
		assertEquals("Male", receivedPerson.getGender());
	}
	*/

	/*
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ADRIANO_ERRO)
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
	*/

	private void mockPerson() {
		personVO.setFirstName("First name test");
		personVO.setLastName("Last name test");
		personVO.setAddress("NY - EUA");
		personVO.setGender("Male");
		personVO.setEnabled(true);
	}

}
