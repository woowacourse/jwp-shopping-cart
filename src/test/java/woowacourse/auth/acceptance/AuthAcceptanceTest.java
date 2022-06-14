package woowacourse.auth.acceptance;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원_추가되어_있음;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private final LoginRequest loginRequest = new LoginRequest("email", "Pw123456!");

    public static String 로그인_후_토큰_획득() {
        return requestHttpPost("", new LoginRequest("email", "Pw123456!"), "/auth/login")
                .extract().as(TokenResponse.class).getAccessToken();
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        회원_추가되어_있음();
        String accessToken = 로그인_후_토큰_획득();

        // when
        ValidatableResponse response = requestHttpGet(accessToken, "/customers");

        // then
        response.statusCode(HttpStatus.OK.value());
        response.body(
                "email", equalTo("email"),
                "name", equalTo("user"),
                "phone", equalTo("010-1234-5678"),
                "address", equalTo("address"));
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // when
        ValidatableResponse response = requestHttpPost("", loginRequest, "/auth/login");

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value());
        response.body(containsString("Email 또는 Password가 일치하지 않습니다."));
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        String accessToken = "invalidToken";
        ValidatableResponse response = requestHttpGet(accessToken, "/customers");

        // then
        response.statusCode(HttpStatus.UNAUTHORIZED.value());
        response.body(containsString("유효하지 않거나 만료된 토큰입니다."));
    }

    @DisplayName("Bearer Auth 토큰 정보가 존재하지 않는 경우")
    @Test
    void myInfoWithNoBearerAuth() {
        // when
        ValidatableResponse response = requestHttpGet("", "/customers");

        // then
        response.statusCode(HttpStatus.UNAUTHORIZED.value());
        response.body(containsString("토큰 정보가 존재하지 않습니다."));
    }
}
