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

@SpringBootAcceptanceTest
public class CustomerAcceptanceTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() {
		// given
		// when
		ExtractableResponse<Response> response = sighUp(email, password, nickname);

		String email = response.jsonPath().getString("email");
		String nickname = response.jsonPath().getString("nickname");

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(email).isEqualTo("123@gmail.com"),
			() -> assertThat(nickname).isEqualTo("does")
		);
	}

	@DisplayName("토큰이 없을 때 회원 탈퇴를 할 수 없다.")
	@Test
	void signOutNotLogin() {
		// given
		sighUp(email, password, nickname);

		// when
		ExtractableResponse<Response> response = signOut("");

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@DisplayName("회원 탈퇴를 진행한다.")
	@Test
	void signOutSuccess() {
		// given
		sighUp(email, password, nickname);
		ExtractableResponse<Response> loginResponse = login(email, password);
		String token = loginResponse.jsonPath().getString("accessToken");

		// when
		ExtractableResponse<Response> response = signOut(token);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	private ExtractableResponse<Response> sighUp(String email, String password, String nickname) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new CustomerRequest(email, password, nickname))
			.when().post("/customers")
			.then().log().all()
			.extract();
	}

	private ExtractableResponse<Response> login(String email, String password) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest(email, password))
			.when().post("/auth/login")
			.then().log().all()
			.extract();
	}

	private ExtractableResponse<Response> signOut(String token) {
		return RestAssured.given().log().all()
			.auth().oauth2(token)
			.when().delete("/customers")
			.then().log().all()
			.extract();
	}
}
