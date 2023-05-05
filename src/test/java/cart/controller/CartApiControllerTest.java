package cart.controller;

import static io.restassured.RestAssured.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import cart.controller.dto.CartResponse;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

	@Value("${local.server.port}")
	int port;

	@BeforeEach
	public void setPort() {
		RestAssured.port = port;
	}

	@Nested
	class CrudTest {

		@Test
		@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
		void ReadCartTest() {
			given().auth()
				.preemptive()
				.basic("test@test.com", "test")
				.log().all()
				.when()
				.get("/cart")
				.then()
				.log().all()
				.statusCode(HttpStatus.OK.value());
		}

		@Test
		@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
		void AddCartTest() {
			given().auth()
				.preemptive()
				.basic("test@test.com", "test")
				.log().all()
				.when()
				.post("/cart/{productId}", 1L)
				.then()
				.log().all()
				.statusCode(HttpStatus.CREATED.value());
		}

		@Test
		@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
		void updateCartTest() {
			final CartResponse response = given().auth()
				.preemptive()
				.basic("test@test.com", "test")
				.log().all()
				.when()
				.patch("/cart/{productId}", 1L)
				.then()
				.log().all()
				.statusCode(HttpStatus.OK.value()).extract().as(CartResponse.class);

			Assertions.assertThat(response.getQuantity()).isEqualTo(2);
		}

		@Test
		@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
		void deleteCartTest() {
			given().auth()
				.preemptive()
				.basic("test@test.com", "test")
				.log().all()
				.when()
				.delete("/cart/{productId}", 1L)
				.then()
				.log().all()
				.statusCode(HttpStatus.NO_CONTENT.value());
		}
	}

}
