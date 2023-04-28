package cart.product.controller;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductViewControllerTest {

	@Value("${local.server.port}")
	int port;

	@BeforeEach
	public void setPort() {
		RestAssured.port = port;
	}

	@Test
	void displayHomeTest() {
		given()
			.when()
			.get("/")
			.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.HTML);
	}

	@Test
	void displayAdminTest() {
		given()
			.when()
			.get("/admin")
			.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.HTML);
	}
}
