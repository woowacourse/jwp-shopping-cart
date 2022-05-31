package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.ExceptionResponse;
import woowacourse.shoppingcart.dto.SignupRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String INVALID_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naG8xMDgiLCJpYXQiOjE2NTM5MDg0OTgsImV4cCI6MTY1MzkxMjA5OH0.6XAQq1jsqxnn8zMbW9nNcZ4R-BiIyQvLkraocC1aaaa";
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입 시 username은 3자 이상 ~ 15자 이하로만 이루어져 있어야 된다.")
    @Test
    void validateUsernameLength() {
        // given
        SignupRequest signupRequest = new SignupRequest("do", "ehdgh1234", "01022728572", "인천 서구 검단로 851 동부아파트 108동 303호");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
        );
    }

    @DisplayName("여러 필드의 검증이 실패한경우 에러메세지를 모두 리스트로 담아 보내야 한다.")
    @Test
    void validateFields() {
        // given
        SignupRequest signupRequest = new SignupRequest("do", "a", "1", "인천 서구 검단로 851 동부아파트 108동 303호");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(3)
        );
    }

    @DisplayName("로그인")
    @Test
    void login() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        // when
        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        String accessToken = RestAssured.given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/login")
            .then().log().all()
            .extract().as(TokenResponse.class).getAccessToken();

        // then
        assertThat(jwtTokenProvider.validateToken(accessToken)).isTrue();
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        String accessToken = RestAssured
            .given().log().all()
            .body(new LoginRequest("dongho108", "ehdgh1234"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // when
        CustomerResponse customerResponse = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers")
            .then().log().all()
            .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        assertAll(
            () -> assertThat(customerResponse.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("잘못된 토큰으로 내 정보 조회를 하면 401 에러를 반환해야 한다.")
    @Test
    void getMeByInvalidToken() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        // when
        RestAssured
            .given().log().all()
            .body(new LoginRequest("dongho108", "ehdgh1234"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().extract();

        // then
        ValidatableResponse validatableResponse = RestAssured
            .given().log().all()
            .auth().oauth2(INVALID_ACCESS_TOKEN)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers")
            .then().log().all();
        ExceptionResponse response = validatableResponse.extract().as(ExceptionResponse.class);

        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(response.getMessages())
                .containsExactly("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("해당 아이디가 존재하지 않으면 (401)unauthorized 를 반환해야 한다.")
    @Test
    void loginByInvalidUsername() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        // when
        ValidatableResponse validatableResponse = RestAssured
            .given().log().all()
            .body(new LoginRequest("dongho109", "ehdgh1234"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all();
        ExceptionResponse exceptionResponse = validatableResponse.extract().as(ExceptionResponse.class);

        // then
        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .containsExactly("해당하는 username이 없습니다.")
        );
    }

    @DisplayName("비밀번호가 일치하지 않으면 (401)unauthorized 를 반환해야 한다.")
    @Test
    void loginByInvalidPassword() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();

        // when
        ValidatableResponse validatableResponse = RestAssured
            .given().log().all()
            .body(new LoginRequest("dongho108", "password"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all();
        ExceptionResponse exceptionResponse = validatableResponse.extract().as(ExceptionResponse.class);

        // then
        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .containsExactly("비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
