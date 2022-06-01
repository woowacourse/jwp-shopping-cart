package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInDto;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    public static final String TEST_EMAIL = "test@test.com";
    public static final String TEST_PASSWORD = "testtest";
    public static final String TEST_USERNAME = "test";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        createCustomer();
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        final String accessToken = tokenResponseDto.getAccessToken();

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/customers/"+ tokenResponseDto.getCustomer().getId())
                .then().log().all()
                .extract();

        // then
        final CustomerDto actual = response.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(actual.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        createCustomer();

        // when
        final ExtractableResponse<Response> wrongPasswordResponse = loginCustomer(TEST_EMAIL, "wrongPassword");

        // then
        assertThat(wrongPasswordResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        createCustomer();
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + "invalidToken")
                .when().get("/api/customers/"+ tokenResponseDto.getCustomer().getId())
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    private void createCustomer() {
        SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpDto)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> loginCustomer(final String email, final String password) {
        final SignInDto signInDto = new SignInDto(email, password);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signInDto)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();
    }
}
