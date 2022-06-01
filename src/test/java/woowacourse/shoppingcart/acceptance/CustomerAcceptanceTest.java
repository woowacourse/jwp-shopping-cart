package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("정상적인 회원가입")
    @Test
    void successSignUp() {
        String email = "tonic@email.com";
        String password = "12345678a";
        String nickname = "토닉";

        ExtractableResponse<Response> response = 회원가입_요청(email, password, nickname);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원정보 양식이 잘못됐을 때 400에러를 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidSignUpForm")
    void invalidSignUpFormatRequest(String email, String password, String nickname) {
        ExtractableResponse<Response> response = 회원가입_요청(email, password, nickname);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().jsonPath().getInt("errorCode")).isEqualTo(1000);
        assertThat(response.body().jsonPath().getString("message")).isNotBlank();
    }

    private static Stream<Arguments> provideInvalidSignUpForm() {
        return Stream.of(
                Arguments.of("email", "12345678a", "tonic"),
                Arguments.of("email@email.com", "12345678", "tonic"),
                Arguments.of("email@email.com", "1234a", "tonic"),
                Arguments.of("email@email.com", "1".repeat(20) + "a", "tonic"),
                Arguments.of("email@email.com", "12345678a", "t"),
                Arguments.of("email@email.com", "12345678a", "a".repeat(9))
        );
    }

    @DisplayName("중복된 이메일로 회원가입시 400 응답")
    @Test
    void duplicatedSignUp() {
        String email = "tonic@email.com";
        String password = "12345678a";
        String nickname = "토닉";
        회원가입_요청(email, password, nickname);

        ExtractableResponse<Response> response = 회원가입_요청(email, password, nickname);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.body().jsonPath().getInt("errorCode")).isEqualTo(1001);
        assertThat(response.body().jsonPath().getString("message")).isNotBlank();
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        String email = "email@email.com";
        String password = "12345678a";
        String nickname = "tonic";
        회원가입_요청(email, password, nickname);
        String token = 로그인_요청(email, password).jsonPath()
                .getString("accessToken");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().log().all().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get("/users/me")
                .then().log().all().extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
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
