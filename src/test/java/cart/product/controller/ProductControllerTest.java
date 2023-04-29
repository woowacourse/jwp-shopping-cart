package cart.product.controller;

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

import cart.product.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

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
			ProductRequest productRequest = new ProductRequest("name", 1000L, "image");

			ExtractableResponse<Response> response = saveProducts(productRequest);

			assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		}

		@Test
		void updateProductsTest() {
			ProductRequest productRequest = new ProductRequest("name", 1000L, "image");
			ProductRequest updatedRequest = new ProductRequest("name", 10L, "image");

			saveProducts(productRequest);

			given()
				.log().all()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(updatedRequest)
				.when()
				.put("/products/1")
				.then()
				.log().all()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON);

		}

		@Test
		void deleteProductsTest() {
			ProductRequest productRequest = new ProductRequest("name", 1000L, "image");

			saveProducts(productRequest);

			when()
				.delete("/products/1")
				.then()
				.log().all()
				.statusCode(HttpStatus.NO_CONTENT.value());
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

	@Nested
	class ExceptionTest {

		@ParameterizedTest
		@ValueSource(strings = {"", " "})
		@NullSource
		void nameNullTest(String name) {
			ProductRequest productRequest = new ProductRequest(name, 1000L, "image");

			ExtractableResponse<Response> response = saveProducts(productRequest);

			assertThat(response.body().asPrettyString()).contains("상품명을 입력해주세요.");
		}

		@Test
		void nameSizeTest() {
			ProductRequest productRequest = new ProductRequest("fsd;kljgnad;ofgadfs;kgjadsfkjhsadflk", 1000L, "image");

			ExtractableResponse<Response> response = saveProducts(productRequest);

			assertThat(response.body().asPrettyString()).contains("20 글자 이하만 입력 가능합니다.");
		}

		@Test
		void priceNullTest() {
			ProductRequest productRequest = new ProductRequest("name", null, "image");

			ExtractableResponse<Response> response = saveProducts(productRequest);

			assertThat(response.body().asPrettyString()).contains("상품가격을 입력해주세요.");
		}

		@ParameterizedTest
		@ValueSource(longs = {-1, -100})
		void nameRangeTest(Long price) {
			ProductRequest productRequest = new ProductRequest("name", price, "image");

			ExtractableResponse<Response> response = saveProducts(productRequest);

			assertThat(response.body().asPrettyString()).contains("상품 금액은 0원 이상의 정수만 입력가능 합니다.");
		}

	}

	private ExtractableResponse<Response> saveProducts(ProductRequest productRequest) {

		//given
		return given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(productRequest)
			.when()
			.post("/products")
			.then()
			.extract();
	}
}
