package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.CustomerRequest;

@SpringBootAcceptanceTest
public class CustomerAcceptanceTest {

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "a1234!", "does");

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/customers")
			.then().log().all()
			.extract();

		String email = response.jsonPath().getString("email");
		String nickname = response.jsonPath().getString("nickname");

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(email).isEqualTo("123@gmail.com"),
			() -> assertThat(nickname).isEqualTo("does")
		);

	}
}
