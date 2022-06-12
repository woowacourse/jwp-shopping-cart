package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import woowacourse.customer.dto.CustomerResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.exception.dto.ExceptionResponse;
import woowacourse.customer.dto.SignupRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String INVALID_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naG8xMDgiLCJpYXQiOjE2NTM5MDg0OTgsImV4cCI6MTY1MzkxMjA5OH0.6XAQq1jsqxnn8zMbW9nNcZ4R-BiIyQvLkraocC1aaaa";

    private final String username = "username";
    private final String wrongUsername = "wrongUsername";
    private final String password = "password";
    private final String wrongPassword = "wrongPassword";
    private final String phoneNumber = "01001011212";
    private final String address = "서울시 여러분";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);
        signup(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = login(loginRequest).extract().as(TokenResponse.class).getAccessToken();

        // when
        final CustomerResponse customerResponse = findInfo(accessToken)
            .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        assertAll(
            () -> assertThat(customerResponse.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("해당 아이디가 존재하지 않으면 (401)unauthorized 를 반환해야 한다.")
    @Test
    void myInfoWithInvalidUsername() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);
        signup(signupRequest);

        // when
        final LoginRequest loginRequest = new LoginRequest(wrongUsername, password);
        final ValidatableResponse response = login(loginRequest);
        final ExceptionResponse exceptionResponse = response.extract().as(ExceptionResponse.class);

        // then
        assertAll(
            () -> response.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .containsExactly("해당하는 사용자 이름이 없습니다.")
        );
    }

    @DisplayName("비밀번호가 일치하지 않으면 (401)unauthorized 를 반환해야 한다.")
    @Test
    void myInfoWithInvalidPassword() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);
        signup(signupRequest);

        // when
        final LoginRequest loginRequest = new LoginRequest(username, wrongPassword);
        final ValidatableResponse response = login(loginRequest);
        final ExceptionResponse exceptionResponse = response.extract().as(ExceptionResponse.class);

        // then
        assertAll(
            () -> response.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .containsExactly("로그인 정보가 시스템에 있는 계정과 일치하지 않습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, phoneNumber, address);
        signup(signupRequest);

        // when
        final LoginRequest loginRequest = new LoginRequest(username, password);
        login(loginRequest);

        // then
        final ValidatableResponse validatableResponse = findInfo(INVALID_ACCESS_TOKEN);
        final ExceptionResponse response = validatableResponse.extract().as(ExceptionResponse.class);

        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(response.getMessages())
                .containsExactly("유효하지 않은 토큰입니다.")
        );
    }

    private void signup(final SignupRequest signupRequest) {
        RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();
    }

    private ValidatableResponse login(final LoginRequest loginRequest) {
        return RestAssured.given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/login")
            .then().log().all();
    }

    private ValidatableResponse findInfo(final String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers")
            .then().log().all();
    }
}
