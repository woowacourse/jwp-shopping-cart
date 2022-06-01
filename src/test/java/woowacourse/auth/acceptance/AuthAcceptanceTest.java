package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.http.Header;
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

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        final String accessToken = tokenResponseDto.getAccessToken();

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + accessToken)
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
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        // when
        final ExtractableResponse<Response> wrongPasswordResponse = loginCustomer(TEST_EMAIL, "wrongPassword");

        // then
        assertThat(wrongPasswordResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(new Header(AUTHORIZATION, BEARER + "invalidToken"))
                .when().get("/api/customers/"+ tokenResponseDto.getCustomer().getId())
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
