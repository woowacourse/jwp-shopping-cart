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
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerSignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("로그인 요청")
    @Nested
    class Login extends AcceptanceTest {

        private CustomerSignUpRequest customerSignUpRequest;

        @BeforeEach
        void prepare() {
            customerSignUpRequest = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
            회원_가입_요청(customerSignUpRequest);
        }

        @DisplayName("성공한다.")
        @Test
        void success() {
            TokenRequest request = new TokenRequest("username", "password123");
            ExtractableResponse<Response> response = 로그인_요청(request);
            로그인됨(response);
        }

        @DisplayName("비밀번호가 불일치하여 실패")
        @Test
        void dismatchPassword_fail() {
            TokenRequest request = new TokenRequest("username", "123password");
            ExtractableResponse<Response> response = 로그인_요청(request);
            로그인안됨(response);
        }
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
        회원_가입_요청(customerSignUpRequest);
        TokenRequest request = new TokenRequest("username", "password123");
        String token = 로그인_요청(request).body()
                .as(TokenResponse.class)
                .getAccessToken();

        ExtractableResponse<Response> response = 내_정보_조회(token);
        정보_조회_성공(response);
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }

    public static ExtractableResponse<Response> 로그인_요청(final TokenRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers/login")
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
}
