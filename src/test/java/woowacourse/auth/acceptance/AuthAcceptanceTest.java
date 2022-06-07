package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원정보 양식이 잘못됐을 때 400에러를 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidLoginForm")
    void invalidLoginFormatRequest(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_FORMAT_ERROR_CODE),
                () -> assertThat(response.jsonPath().getString("message")).isNotBlank()
        );
    }

    private static Stream<Arguments> provideInvalidLoginForm() {
        return Stream.of(
                Arguments.of("email", "12345678a"),
                Arguments.of("email@email.com", "12345678"),
                Arguments.of("email@email.com", "1234a"),
                Arguments.of("email@email.com", "1".repeat(20) + "a")
        );
    }

    @DisplayName("존재하지 않는 아이디로 요청이 왔을 때 400에러를 응답")
    @Test
    void notFoundCustomerLoginRequest() {
        ExtractableResponse<Response> response = 로그인_요청("email@email.com", "12345678a");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_LOGIN_ERROR_CODE),
                () -> assertThat(response.jsonPath().getString("message")).isNotBlank()
        );
    }

    @DisplayName("잘못된 비밀번호로 요청이 왔을 때 400에러를 응답")
    @Test
    void invalidPasswordCustomerLoginRequest() {
        String email = "email@email.com";
        String password = "12345678a";
        String nickname = "tonic";
        회원가입_요청(email, password, nickname);

        ExtractableResponse<Response> response = 로그인_요청(email, "12345678b");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_LOGIN_ERROR_CODE),
                () -> assertThat(response.jsonPath().getString("message")).isNotBlank()
        );
    }


    @DisplayName("정상 케이스일 때 토큰과 함께 200을 응답")
    @Test
    void validCustomerLoginRequest() {
        String email = "email@email.com";
        String password = "12345678a";
        String nickname = "tonic";

        회원가입_요청(email, password, nickname);

        ExtractableResponse<Response> response = 로그인_요청(email, password);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("accessToken")).isNotBlank()
        );
    }

    @DisplayName("토큰이 없이 회원 정보를 조회하는 경우 401 반환")
    @Test
    void requestGetMeWithoutToken() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().log().all().get("/users/me")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("유효하지 않는 토큰으로 회원 정보를 조회하는 경우 401 반환")
    @Test
    void requestGetMeWithInvalidToken() {
        ExtractableResponse<Response> response = 회원정보_요청("invalidToken");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("유효한 토큰인 경우 200을 응답")
    @Test
    void requestGetMeWithValidToken() {
        회원가입_요청("email@email.com", "12345678a","tonic");
        String accessToken = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 회원정보_요청(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
