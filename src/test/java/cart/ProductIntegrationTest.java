package cart;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import cart.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

	@Autowired
	ProductRepository repository;
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void getProducts() {
		final ExtractableResponse<Response> result = given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().log().all()
			.get("/products")
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void createProduct() {
		final ProductUpdateRequest request = new ProductUpdateRequest("KIARA", 10000, "이미지");

		final ExtractableResponse<Response> result = given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().log().all()
			.post("/products")
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void deleteProduct() {
		final Product product = new Product("apple", 1000, "사과이미지");
		final ProductId productId = repository.save(product);

		final ExtractableResponse<Response> result = given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", productId.getId())
			.when().log().all()
			.delete("/products/{id}")
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	@Test
	public void updateProduct() {
		final ProductId oldProductId = repository.save(new Product("apple", 1000, "사과이미지"));

		final ProductUpdateRequest request = new ProductUpdateRequest("orange", 1500, "오렌지이미지");

		final ExtractableResponse<Response> result = given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", oldProductId.getId())
			.body(request)
			.when().log().all()
			.patch("/products/{id}")
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
