package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.application.CustomerService;

@SpringBootAcceptanceTest
public class AuthControllerTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@Autowired
	private CustomerService customerService;

	@DisplayName("로그인이 성공한다.")
	@Test
	void login() {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest("123@gmail.com", "a1234!"))
			.when().post("/auth/login")
			.then().log().all()
			.extract();

		// then
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("nickname")).isEqualTo("does"),
			() -> assertThat(response.jsonPath().getString("accessToken")).isNotNull()
		);
	}

	@DisplayName("로그인에 실패한다.")
	@Test
	void loginFail() {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest(email, "a12232134!"))
			.when().post("/auth/login")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
}
