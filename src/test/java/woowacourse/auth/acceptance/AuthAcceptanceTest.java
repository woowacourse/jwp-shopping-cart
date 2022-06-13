package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.내_정보_조회;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원_가입_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.PasswordRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password123";
    private static final String PHONE_NUMBER = "01012345678";
    private static final String ADDRESS = "성담빌딩";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        String token = 회원_가입_후_로그인();

        ExtractableResponse<Response> response = 내_정보_조회(token);
        정보_조회_성공(response);
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        ExtractableResponse<Response> response = 내_정보_조회("fake.token.value");
        정보_조회_실패(response);
    }

    @DisplayName("모든 주소에 대한 OPTIONS 요청은 토큰이 없어도 유효")
    @Test
    void optionsMethod_withoutToken_success() {
        ExtractableResponse<Response> response = 내_정보_조회_OPTIONS();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("로그인 요청")
    @Nested
    class Login extends AcceptanceTest {

        private CustomerSignUpRequest customerSignUpRequest;

        @BeforeEach
        void prepare() {
            customerSignUpRequest = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
            회원_가입_요청(customerSignUpRequest);
        }

        @DisplayName("성공한다.")
        @Test
        void success() {
            TokenRequest request = new TokenRequest(USERNAME, PASSWORD);
            ExtractableResponse<Response> response = 로그인_요청(request);
            로그인됨(response);
        }

        @DisplayName("비밀번호가 불일치하여 실패")
        @Test
        void mismatchPassword_fail() {
            TokenRequest request = new TokenRequest(USERNAME, "123password");
            ExtractableResponse<Response> response = 로그인_요청(request);
            로그인안됨(response);
        }
    }

    @DisplayName("비밀번호 확인 요청")
    @Nested
    class CheckPassword extends AcceptanceTest {

        private CustomerSignUpRequest customerSignUpRequest;
        private String accessToken;

        @BeforeEach
        void prepare() {
            customerSignUpRequest = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
            회원_가입_요청(customerSignUpRequest);
            accessToken = 로그인_요청(new TokenRequest(USERNAME, PASSWORD))
                    .body()
                    .as(TokenResponse.class)
                    .getAccessToken();
        }

        @DisplayName("성공한다.")
        @Test
        void success() {
            ExtractableResponse<Response> response = 비밀번호_확인_요청(accessToken, PASSWORD);
            비밀번호_일치(response);
        }

        @DisplayName("불일치하여 실패.")
        @Test
        void mismatchPassword_fail() {
            ExtractableResponse<Response> response = 비밀번호_확인_요청(accessToken, "wrongpassword123");
            비밀번호_불일치(response);
        }
    }

    public static String 회원_가입_후_로그인() {
        CustomerSignUpRequest request = new CustomerSignUpRequest(USERNAME, PASSWORD, PHONE_NUMBER, ADDRESS);
        회원_가입_요청(request);
        TokenRequest tokenRequest = new TokenRequest(USERNAME, PASSWORD);
        return 로그인_요청(tokenRequest).body()
                .as(TokenResponse.class)
                .getAccessToken();
    }

    private static ExtractableResponse<Response> 내_정보_조회_OPTIONS() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().options("/api/customer")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_요청(final TokenRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customer/login")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 비밀번호_확인_요청(final String accessToken, final String password) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PasswordRequest(password))
                .when().post("/api/customer/password")
                .then().log().all()
                .extract();
    }

    private void 로그인됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 로그인안됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void 정보_조회_성공(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 정보_조회_실패(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private void 비밀번호_일치(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 비밀번호_불일치(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
