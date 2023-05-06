package cart;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.repository.CartRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {
	@Autowired
	CartRepository repository;
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void findAllByMemberEmail(){
		final ExtractableResponse<Response> result = RestAssured.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().preemptive().basic("a@a.com", "pw")
			.when().log().all()
			.get("/carts")
			.then().log().all()
			.extract();

		Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
