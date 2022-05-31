package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;

@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "12345678";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void login() {
        // given 회원이 등록되어 있고
        CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer")
                .then().log().all()
                .extract();

        // when id, password를 사용해 로그인 시도를 하면
        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest(EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // then 요청이 성공하고 토큰이 발급된다.
        assertThat(jwtTokenProvider.validateToken(accessToken)).isTrue();
    }

    @DisplayName("Bearer Auth 로그인 실패 - 존재하지 않는 이메일")
    @Test
    void login_wrongEmail_badRequest() {
        // given 회원이 등록되어 있고
        CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer")
                .then().log().all()
                .extract();

        // when 존재하지 않는 이메일을 사용해 토큰을 요청하면
        CustomerRegisterRequest invalidRequest = new CustomerRegisterRequest(NAME, "lalala@gamil.com", PASSWORD);
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(invalidRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();

        // then 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().asString()).isEqualTo("존재하지 않는 이메일입니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 실패 - 일치하지 않는 비밀번호")
    @Test
    void login_wrongPassword_badRequest() {
        // given 회원이 등록되어 있고
        CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer")
                .then().log().all()
                .extract();

        // when 이메일에 일치하지 않는 비밀번호를 사용해 토큰을 요청하면
        CustomerRegisterRequest invalidRequest = new CustomerRegisterRequest(NAME, EMAIL, "1111111111");
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(invalidRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();

        // then 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().asString()).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then 내 정보 조회 요청이 거부된다
    }
}
