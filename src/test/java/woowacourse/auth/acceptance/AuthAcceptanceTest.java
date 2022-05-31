package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.acceptance.RequestHandler;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwe123!@#";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void loginSuccess() {
        // given
        RequestHandler.postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = RequestHandler.postRequest(
                "/auth/login", new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);
        assertThat(tokenResponse.getUserName()).isEqualTo(CUSTOMER_NAME);
    }

    @DisplayName("로그인 실패 - 이메일이 존재하지 않는 경우")
    @Test
    void loginFailedWithNoSuchEmail() {
        // given
        RequestHandler.postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = RequestHandler.postRequest(
                "/auth/login", new TokenRequest("noGuest@woowa.com", CUSTOMER_PASSWORD));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        final ExceptionResponse exceptionResponse = response.jsonPath().getObject(".", ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("이메일에 해당하는 회원이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않는 경우")
    @Test
    void loginFailedWithWrongPassword() {
        // given
        RequestHandler.postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = RequestHandler.postRequest(
                "/auth/login", new TokenRequest(CUSTOMER_EMAIL, "wrongPassword"));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        final ExceptionResponse exceptionResponse = response.jsonPath().getObject(".", ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("이메일에 해당하는 회원이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
