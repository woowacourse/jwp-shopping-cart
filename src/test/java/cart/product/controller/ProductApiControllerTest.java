package cart.product.controller;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

	@Value("${local.server.port}")
	int port;

	@BeforeEach
	public void setPort() {
		RestAssured.port = port;
	}

	@Test
	void createProductsTest() {
		//given
		given()
				.config(config()
					.encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
				.param("name", "name")
				.param("price", 1000)
				.param("image", "image")
			.when()
				.post("/products")
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.contentType(ContentType.JSON);
	}
}
