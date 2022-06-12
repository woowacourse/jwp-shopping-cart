package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.request.TokenRequest;
import woowacourse.auth.dto.response.TokenResponse;
import woowacourse.setup.AcceptanceTest;
import woowacourse.auth.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password1@";

    @BeforeEach
    void setup() {
        회원가입_요청();
    }

    @DisplayName("POST /login - 로그인 요청에 대한 검증")
    @Nested
    class AuthenticationTest {

        @Test
        void 로그인_성공시_200() {
            TokenRequest 유효한_인증정보 = new TokenRequest(USERNAME, PASSWORD);

            ExtractableResponse<Response> response = 로그인_요청(유효한_인증정보);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 로그인_실패시_401() {
            TokenRequest 비밀번호가_잘못된_정보 = new TokenRequest(USERNAME, "wrong123@#$%");

            ExtractableResponse<Response> response = 로그인_요청(비밀번호가_잘못된_정보);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        void 로그인에_필요한_정보가_하나라도_누락된_경우_400() {
            Map<String, Object> 비밀번호_누락 = new HashMap<>() {{
                put("username", USERNAME);
            }};

            ExtractableResponse<Response> response = 로그인_요청(비밀번호_누락);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("로그인 성공 후 Bearer 토큰을 활용한 인가 과정")
    @Nested
    class AuthorizationTest {

        @Test
        void 토큰이_유효한_경우_인가_성공() {
            String 유효한_토큰 = 유효한_로그인_요청();

            ExtractableResponse<Response> response = 내_정보_조회_요청(유효한_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 토큰이_유효하지_않은_경우_인가_실패_401() {
            String 잘못된_토큰 = "안녕하세요.해커입니다.";

            ExtractableResponse<Response> response = 내_정보_조회_요청(잘못된_토큰);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        void 토큰이_없는_경우_인증_실패_401() {
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/customers/me")
                    .then().log().all()
                    .extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public static void 회원가입_요청() {
        SignUpRequest newCustomer = new SignUpRequest(USERNAME, PASSWORD, "닉네임", 15);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCustomer)
                .when().post("/customers")
                .then().log().all();
    }

    public static String 유효한_로그인_요청() {
        return 로그인_요청(new TokenRequest(USERNAME, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();
    }

    private static ExtractableResponse<Response> 로그인_요청(Object json) {
        return RestAssured
                .given().log().all()
                .body(json)
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
