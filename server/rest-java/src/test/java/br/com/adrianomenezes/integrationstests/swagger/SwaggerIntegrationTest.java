package br.com.adrianomenezes.integrationstests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianomenezes.configs.TestConfig;
import br.com.adrianomenezes.integrationstests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = given()
						.basePath("/swagger-ui/index.html")
						.port(TestConfig.SERVER_PORT)
						.when()
							.get()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();
		assertTrue(content.contains("Swagger UI"));
}

}
