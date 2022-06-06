package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

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
		RestUtils.signUp(email, password, nickname);

		// when
		ExtractableResponse<Response> loginResponse = RestUtils.login(email, password);
		// then
		assertAll(
			() -> assertThat(loginResponse.jsonPath().getString("nickname")).isEqualTo("does"),
			() -> assertThat(loginResponse.jsonPath().getString("accessToken")).isNotNull()
		);
	}

	@DisplayName("비밀 번호를 틀리면 로그인하지 못한다.")
	@Test
	void myInfoWithInvalidPassword() {
		// given
		RestUtils.signUp(email, password, nickname);

		// when
		ExtractableResponse<Response> loginResponse = RestUtils.login(email, "a1234!!!23");

		// then
		assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@DisplayName("이메일을 틀리면 로그인하지 못한다.")
	@Test
	void myInfoWithInvalidEmail() {
		// given
		RestUtils.signUp(email, password, nickname);

		// when
		ExtractableResponse<Response> loginResponse = RestUtils.login("email@gmail.com", password);

		// then
		assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
}
