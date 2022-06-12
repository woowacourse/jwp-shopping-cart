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
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.customer.dto.CustomerRequest;
import woowacourse.shoppingcart.customer.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final String email = "test@a.com";
        final String password = "password0!";
        final String username = "테스트";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);

        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");

        // when
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token, "/api/customers/me");

        // then
        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        final String email = "test@a.com";
        final String password = "password0!";
        final String username = "테스트";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);

        postMethodRequest(customerRequest, "/api/customers");

        // when
        final LoginRequest loginRequest = new LoginRequest(email, "diffPwd0!");
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");
        final int errorCode = tokenResponse.jsonPath().getInt("errorCode");

        // then
        assertAll(
                ()-> assertThat(errorCode).isEqualTo(2001),
                ()-> assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth("Bearer 123", "/api/customers/me");
        final int errorCode = response.jsonPath().getInt("errorCode");

        // then
        assertAll(
                ()-> assertThat(errorCode).isEqualTo(3002),
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        );
    }
}
