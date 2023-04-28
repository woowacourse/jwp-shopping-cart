package cart.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.controller.dto.ExceptionResponse;
import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

	@Value("${local.server.port}")
	int port;

	@BeforeEach
	public void setPort() {
		RestAssured.port = port;
	}

	@Nested
	class CrudTest {
		@Test
		void createProductsTest() {
			ProductRequest request = new ProductRequest("name", 1000, "image");
			ProductResponse response = saveProducts(request, HttpStatus.CREATED.value()).as(ProductResponse.class);

			assertThat(response.getName()).isEqualTo(request.getName());
			assertThat(response.getPrice()).isEqualTo(request.getPrice());
			assertThat(response.getImage()).isEqualTo(request.getImage());
		}

		@Test
		void updateProductsTest() {
			ProductRequest productRequest = new ProductRequest("name", 1000, "image");
			ProductRequest updatedRequest = new ProductRequest("name", 10, "image");

			saveProducts(productRequest, HttpStatus.CREATED.value());

			ProductResponse response = given()
				.log().all()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(updatedRequest)
				.when()
				.put("/products/1")
				.then()
				.log().all()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().as(ProductResponse.class);

			assertThat(response.getName()).isEqualTo(updatedRequest.getName());
			assertThat(response.getPrice()).isEqualTo(updatedRequest.getPrice());
			assertThat(response.getImage()).isEqualTo(updatedRequest.getImage());

		}

		@Test
		void deleteProductsTest() {
			ProductRequest productRequest = new ProductRequest("name", 1000, "image");

			saveProducts(productRequest, HttpStatus.CREATED.value());

			when()
				.delete("/products/1")
				.then()
				.log().all()
				.statusCode(HttpStatus.NO_CONTENT.value());
		}
	}

	@Nested
	class ExceptionTest {

		@ParameterizedTest
		@ValueSource(strings = {"", " "})
		@NullSource
		void nameNullTest(String name) {
			ProductRequest productRequest = new ProductRequest(name, 1000, "image");

			ExceptionResponse response = saveProducts(productRequest, HttpStatus.BAD_REQUEST.value()).as(
				ExceptionResponse.class);

			assertThat(response.getExceptionMessage()).isEqualTo("상품명을 입력해주세요.");
		}

		@Test
		void nameSizeTest() {
			ProductRequest productRequest = new ProductRequest("thisIs20OverLengthString", 1000, "image");

			ExceptionResponse response = saveProducts(productRequest, HttpStatus.BAD_REQUEST.value()).as(
				ExceptionResponse.class);

			assertThat(response.getExceptionMessage()).isEqualTo("20 글자 이하만 입력 가능합니다.");
		}

		@Test
		void priceNullTest() {
			ProductRequest productRequest = new ProductRequest("name", null, "image");

			ExceptionResponse response = saveProducts(productRequest, HttpStatus.BAD_REQUEST.value()).as(
				ExceptionResponse.class);

			assertThat(response.getExceptionMessage()).isEqualTo("상품가격을 입력해주세요.");
		}

		@ParameterizedTest
		@ValueSource(ints = {-1, -100})
		void nameRangeTest(int price) {
			ProductRequest productRequest = new ProductRequest("name", price, "image");

			ExceptionResponse response = saveProducts(productRequest, HttpStatus.BAD_REQUEST.value()).as(
				ExceptionResponse.class);

			assertThat(response.getExceptionMessage()).isEqualTo("상품 금액은 0원 이상의 정수만 입력가능 합니다.");
		}

	}

	private ExtractableResponse<Response> saveProducts(ProductRequest productRequest, int httpStatusCode) {

		//given
		return given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(productRequest)
			.when()
			.post("/products")
			.then()
			.statusCode(httpStatusCode)
			.extract();
	}
}
