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
import woowacourse.auth.dto.TokenRequest;

@DisplayName("인증 관련 기능")
@SpringBootAcceptanceTest
public class AuthAcceptanceTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@DisplayName("로그인 성공")
	@Test
	void myInfoWithBearerAuth() {
		// given
		signUp(new CustomerRequest(email, password, nickname));

		// when
		ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest(email, password))
			.when().post("/auth/login")
			.then().log().all()
			.extract();

		// then
		assertAll(
			() -> assertThat(loginResponse.jsonPath().getString("nickname")).isEqualTo("does"),
			() -> assertThat(loginResponse.jsonPath().getString("accessToken")).isNotNull()
		);
	}

	@DisplayName("로그인 실패")
	@Test
	void myInfoWithBadBearerAuth() {
		// given
		signUp(new CustomerRequest(email, password, nickname));

		// when
		ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest(email, "a1234!!!23"))
			.when().post("/auth/login")
			.then().log().all()
			.extract();

		// then
		assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	private void signUp(CustomerRequest request) {
		RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/customers")
			.then().log().all()
			.extract();
	}
}
