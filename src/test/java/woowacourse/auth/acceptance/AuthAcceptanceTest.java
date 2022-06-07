package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원가입;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = 로그인(tokenRequest);

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerResponse customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo(NAME),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패 - 이메일 불 일치")
    @Test
    void myInfoWithBadBearerAuthInvalidEmail() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        TokenRequest tokenRequest = new TokenRequest("test@test.com", PASSWORD);
        ExtractableResponse<Response> loginResponse = RestAssured
                .given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/auth/login")
                .then().log().all()
                .extract();

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(() -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(loginResponse.jsonPath().getString("message")).isEqualTo("존재하지 않는 이메일 입니다."));
    }

    @DisplayName("Bearer Auth 로그인 실패 - 비밀번호 불 일치")
    @Test
    void myInfoWithBadBearerAuthInvalidPassword() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        TokenRequest tokenRequest = new TokenRequest(EMAIL, "Bunny1234!");
        ExtractableResponse<Response> loginResponse = RestAssured
                .given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/auth/login")
                .then().log().all()
                .extract();

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(() -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(loginResponse.jsonPath().getString("message")).isEqualTo("비밀번호가 일치하지 않습니다."));
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = 로그인(tokenRequest);

        ExtractableResponse<Response> customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken + "l")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers")
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertAll(() -> assertThat(customerResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(customerResponse.jsonPath().getString("message")).isEqualTo("유효하지 않은 토큰입니다."));
    }

    public static String 로그인(TokenRequest tokenRequest) {
        return RestAssured
                .given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/auth/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
    }
}
