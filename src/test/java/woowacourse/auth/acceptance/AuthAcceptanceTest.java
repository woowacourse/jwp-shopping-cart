package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.setup.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "valid_username";
    private static final String PASSWORD = "password";

    @BeforeEach
    void setup() {
        회원가입_요청();
    }

    @Test
    void 로그인_성공() {
        TokenRequest 유효한_인증정보 = new TokenRequest(USERNAME, PASSWORD);

        ExtractableResponse<Response> response = 로그인_요청(유효한_인증정보);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 로그인_실패() {
        TokenRequest 잘못된_인증정보 = new TokenRequest(USERNAME, "wrong_password");

        ExtractableResponse<Response> response = 로그인_요청(잘못된_인증정보);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 토큰이_유효한_경우_인가_성공() {
        String 유효한_토큰 = 유효한_로그인_요청();

        ExtractableResponse<Response> response = 내_정보_조회_요청(유효한_토큰);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 토큰이_유효하지_않은_경우_인가_실패() {
        String 잘못된_토큰 = "안녕하세요.해커입니다.";

        ExtractableResponse<Response> response = 내_정보_조회_요청(잘못된_토큰);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 토큰이_없는_경우_인증_실패() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    private void 회원가입_요청() {
        SignUpRequest newCustomer = new SignUpRequest(USERNAME, PASSWORD, "닉네임", 15);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCustomer)
                .when().post("/customers")
                .then().log().all();
    }

    private String 유효한_로그인_요청() {
        return 로그인_요청(new TokenRequest(USERNAME, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();
    }

    private ExtractableResponse<Response> 로그인_요청(TokenRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 내_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .extract();
    }
}
