package woowacourse.shoppingcart.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;
import static woowacourse.support.acceptance.RestHandler.extractResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.customer.acceptance.CustomerRestHandler;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.support.acceptance.AcceptanceTest;

class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        CustomerRestHandler.회원가입(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
    }

    @DisplayName("로그인을 한다.")
    @Test
    void loginSuccess() {
        final ExtractableResponse<Response> loginResponse = AuthRestHandler.로그인(EMAIL_VALUE, PASSWORD_VALUE);

        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertAll(
                () -> assertThat(extractResponse(loginResponse, TokenResponse.class).getAccessToken())
                        .isNotNull(),
                () -> assertThat(extractResponse(loginResponse, TokenResponse.class).getNickname())
                        .isEqualTo(NICKNAME_VALUE)
        );

    }

    @DisplayName("존재하지 않는 이메일로 로그인을 한다.")
    @Test
    void loginWithNonExistentEmail() {
        final ExtractableResponse<Response> loginResponse = AuthRestHandler.로그인("wrong" + EMAIL_VALUE, PASSWORD_VALUE);

        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        CustomerRestHandler.assertThatException(loginResponse, CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD);
    }

    @DisplayName("일치하지 않는 비밀번호로 로그인을 한다.")
    @Test
    void loginWithMismatchedPassword() {
        final ExtractableResponse<Response> loginResponse = AuthRestHandler.로그인(EMAIL_VALUE, "wrong" + PASSWORD_VALUE);

        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        CustomerRestHandler.assertThatException(loginResponse, CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD);
    }

    @DisplayName("로그아웃을 한다.")
    @Test
    void logout() {
        final String accessToken = extractResponse(AuthRestHandler.회원가입_로그인(), TokenResponse.class).getAccessToken();

        final ExtractableResponse<Response> logoutResponse = AuthRestHandler.로그아웃(accessToken);

        assertThat(logoutResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰 인증 실패")
    @Nested
    class TokenTest {

        @DisplayName("토큰의 기간이 만료된 경우")
        @Test
        void tokenExpired() {
            final String expiredToken =
                    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjUzOTkzMzM3LCJleHAiOjE2NTM5OTMzMzd9."
                            + "rlmlgHw_zjq7eY4FAgBU3Fx2Pq9rUgSdE9le9kpwd4w";
            final ExtractableResponse<Response> response = AuthRestHandler.로그아웃(expiredToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @DisplayName("토큰의 무결성이 깨진 경우")
        @Test
        void tokenTampered() {
            final String brokenToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiaWF0IjoxNjUzOTkzMzM3LCJleHAiOjE2NTM5OTMzMzd9."
                    + "rlmlgHw_zjq7eY4FAgBU3Fx2Pq9rUgSdE9le9kpwd4w";
            final ExtractableResponse<Response> response = AuthRestHandler.로그아웃(brokenToken);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
