package woowacourse.auth.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredConvenienceMethod;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.member.ui.dto.SignUpRequest;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String accessToken = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        RestAssuredConvenienceMethod.getRequestWithToken(accessToken, "/api/members/me")
                .statusCode(HttpStatus.OK.value())
                .assertThat().body("email", equalTo("pobi@wooteco.com"))
                .assertThat().body("name", equalTo("포비"));
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco123!");
        RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .assertThat().body("message", equalTo("잘못된 비밀번호입니다."));
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        SignUpRequest signUpRequest = new SignUpRequest("pobi@wooteco.com", "포비", "Wooteco1!");
        RestAssuredConvenienceMethod.postRequest(signUpRequest, "/api/members");

        LoginRequest loginRequest = new LoginRequest("pobi@wooteco.com", "Wooteco1!");
        String token = RestAssuredConvenienceMethod.postRequest(loginRequest, "/api/auth")
                .extract().as(LoginResponse.class).getAccessToken();

        token = token.toLowerCase();
        RestAssuredConvenienceMethod.getRequestWithToken(token, "/api/members/me")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat().body("message", equalTo("유효하지 않은 토큰입니다."));
    }
}
