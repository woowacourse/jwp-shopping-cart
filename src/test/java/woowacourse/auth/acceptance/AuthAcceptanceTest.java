package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.사용자_생성_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.LoginRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId@gmail.com", "12345678aA!");
        ExtractableResponse<Response> response = 로그인_요청(loginRequest);
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        // when
        CustomerResponse expected = 내_정보_조회_요청(tokenResponse).as(CustomerResponse.class);
        // then
        assertAll(() -> {
            assertThat(expected).extracting("loginId", "name")
                    .containsExactly("loginId@gmail.com", "seungpapang");
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });

    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");

        // when
        LoginRequest loginRequest = new LoginRequest("invalidLoginId@gmail.com", "12345678aA!");
        ExtractableResponse<Response> response = 로그인_요청(loginRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        TokenResponse badTokenResponse = new TokenResponse("invalidToken", "name");
        ExtractableResponse<Response> response = 내_정보_조회_요청(badTokenResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static ExtractableResponse<Response> 로그인_요청(LoginRequest loginRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .extract();
    }
}
