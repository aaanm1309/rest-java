package br.com.adrianomenezes.integrationstests.controller.withyml;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianomenezes.integrationstests.controller.withyml.mapper.YmlMapper;
import br.com.adrianomenezes.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.adrianomenezes.integrationstests.vo.TokenVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYmlTest extends AbstractIntegrationTest{
	
	private static YmlMapper mapper;
	private static TokenVO tokenVO;
	
	@BeforeAll
	public static void setup() {
		mapper =  new YmlMapper();
	}
	
//	@Test
//	@Order(1)
//	public void testSignin() throws JsonMappingException, JsonProcessingException {
//		AccountCredentialsVO user = new AccountCredentialsVO("adriano", "admin123");
//		tokenVO = given()
//				.config(
//					RestAssuredConfig.config()
//						.encoderConfig(EncoderConfig
//								.encoderConfig()
//								.encodeContentTypeAs(
//										TestConfig.CONTENT_TYPE_YML,
//										ContentType.TEXT)))
//				.accept(TestConfig.CONTENT_TYPE_YML)
//				.basePath("/auth/signin")
//					.port(TestConfig.SERVER_PORT)
//					.contentType(TestConfig.CONTENT_TYPE_YML)
//				.body(user, mapper)
//				.when()
//					.post()
//				.then()
//					.statusCode(200)
//				.extract()
//					.body()
//						.as(TokenVO.class, mapper);
//
////		assertNotNull(tokenVO.getAccessToken());
////		assertNotNull(tokenVO.getRefreshToken());
//
//
//	}
//	
//	
//	@Test
//	@Order(2)
//	public void testRefresh() throws JsonMappingException, JsonProcessingException {
//
//		var newTokenVO = given()
//				.config(
//						RestAssuredConfig.config()
//							.encoderConfig(EncoderConfig
//									.encoderConfig()
//									.encodeContentTypeAs(
//											TestConfig.CONTENT_TYPE_YML,
//											ContentType.TEXT)))
//				.accept(TestConfig.CONTENT_TYPE_YML)
//				.basePath("/auth/refresh")
//				.port(TestConfig.SERVER_PORT)
//				.contentType(TestConfig.CONTENT_TYPE_YML)
//					.pathParam("username", tokenVO.getUsername())
//					.header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
//				.when()
//				.put("{username}")
//				.then()
//				.statusCode(200)
//				.extract()
//				.body()
//				.as(TokenVO.class, mapper);
//		
//		assertNotNull(newTokenVO.getAccessToken());
//		assertNotNull(newTokenVO.getRefreshToken());
////		
//		
//	}

}
