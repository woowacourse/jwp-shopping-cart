package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private final String email = "test@a.com";
    private final String password = "password0!";
    private final String username = "테스트";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");

        // when
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token, "/api/customers/me");

        // then
        final CustomerResponse customerResponse = response.jsonPath()
                .getObject(".", CustomerResponse.class);
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username)
        );
    }

    @DisplayName("로그인 시 이메일 규약에 맞지 않는 이메일을 입력할 경우 에러를 발생한다.")
    @Test
    void inputInvalidEmail() {
        // given
        final String invalidEmail = "test.a.com";
        final LoginRequest loginRequest = new LoginRequest(invalidEmail, password);

        // when
        final ExtractableResponse<Response> response = postMethodRequest(loginRequest, "/api/auth/login");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(ErrorResponse.INVALID_EMAIL.getErrorCode()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorResponse.INVALID_EMAIL.getMessage())
        );
    }

    @DisplayName("잘못된 이메일을 입력할 경우 에러를 발생한다.")
    @Test
    void inputWrongEmail() {
        // given
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        // when
        final String wrongEmail = "aki@test.com";
        final LoginRequest loginRequest = new LoginRequest(wrongEmail, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");
        final int errorCode = tokenResponse.jsonPath().getInt("errorCode");

        // then
        assertAll(
                () -> assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(tokenResponse.jsonPath().getInt("errorCode")).isEqualTo(ErrorResponse.LOGIN_FAIL.getErrorCode()),
                () -> assertThat(tokenResponse.jsonPath().getString("message")).isEqualTo(ErrorResponse.LOGIN_FAIL.getMessage())
        );
    }

    @DisplayName("패스워드가 일치하지 않을 경우 에러를 발생한다.")
    @Test
    void inputWrongPassword() {
        // given
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        // when
        final String wrongPassword = "diffPwd0!";
        final LoginRequest loginRequest = new LoginRequest(email, wrongPassword);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");
        final int errorCode = tokenResponse.jsonPath().getInt("errorCode");

        // then
        assertAll(
                () -> assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(tokenResponse.jsonPath().getInt("errorCode")).isEqualTo(ErrorResponse.LOGIN_FAIL.getErrorCode()),
                () -> assertThat(tokenResponse.jsonPath().getString("message")).isEqualTo(ErrorResponse.LOGIN_FAIL.getMessage())
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth("Bearer 123", "/api/customers/me");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(ErrorResponse.INVALID_TOKEN.getErrorCode()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorResponse.INVALID_TOKEN.getMessage())
        );
    }
}
