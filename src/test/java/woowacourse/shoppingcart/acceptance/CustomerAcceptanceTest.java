package woowacourse.shoppingcart.acceptance;

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
        assertThat(response.body().jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_FORMAT_ERROR_CODE);
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
        assertThat(response.body().jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_DUPLICATE_EMAIL_ERROR_CODE);
        assertThat(response.body().jsonPath().getString("message")).isNotBlank();
    }

    @DisplayName("정상적인 회원 정보 조회 시 200 응답")
    @Test
    void getMe() {
        String email = "email@email.com";
        String password = "12345678a";
        String nickname = "tonic";
        회원가입_요청(email, password, nickname);
        String token = 토큰_요청(email, password);

        ExtractableResponse<Response> response = 회원정보_요청(token);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
        );
    }

    @DisplayName("잘못된 토큰으로 회원 탈퇴 시 401 반환")
    @Test
    void deleteByInvalidToken() {
        ExtractableResponse<Response> response = 회원탈퇴_요청("invalidToken");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("존재하는 회원 탈퇴 시 204 반환")
    @Test
    void deleteByValidToken() {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> 회원탈퇴_응답 = 회원탈퇴_요청(token);
        ExtractableResponse<Response> 회원정보_응답 = 회원정보_요청(token);

        assertAll(
                () -> assertThat(회원탈퇴_응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(회원정보_응답.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(회원정보_응답.body().jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_LOGIN_ERROR_CODE)
        );
    }

    @DisplayName("잘못된 토큰으로 회원 정보 수정시 401 반환")
    @Test
    void updateByInvalidToken() {
        String token = "invalidToken";
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .put("/users/me")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("잘못된 회원 정보 양식으로 수정시 400 반환")
    @ParameterizedTest
    @MethodSource("provideInvalidUpdateForm")
    void updateByInvalidFormat(String password, String nickname) {
        회원가입_요청("email@email.com", "12345678a", "tonic");
        String token = 토큰_요청("email@email.com", "12345678a");

        ExtractableResponse<Response> response = 회원정보_수정_요청(nickname, password, token);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getInt("errorCode")).isEqualTo(CUSTOMER_INVALID_FORMAT_ERROR_CODE)
        );
    }

    private static Stream<Arguments> provideInvalidUpdateForm() {
        return Stream.of(
                Arguments.of("12345678", "tonic"),
                Arguments.of("1234a", "tonic"),
                Arguments.of("1".repeat(20) + "a", "tonic"),
                Arguments.of("12345678a", "t"),
                Arguments.of("12345678a", "a".repeat(9))
        );
    }

    @DisplayName("정상적인 회원 정보 수정 시 204 반환")
    @Test
    void updateCustomer() {
        String email = "email@email.com";
        회원가입_요청(email, "12345678a", "tonic");
        String token = 토큰_요청(email, "12345678a");

        String newNickName = "토닉";
        String newPassword = "newpassword1";

        ExtractableResponse<Response> 회원정보_수정_응답 = 회원정보_수정_요청(newNickName, newPassword, token);
        ExtractableResponse<Response> 수정후_회원정보_응답 = 회원정보_요청(token);
        ExtractableResponse<Response> 수정후_로그인_응답 = 로그인_요청(email, newPassword);

        assertAll(
                () -> assertThat(회원정보_수정_응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(수정후_회원정보_응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(수정후_회원정보_응답.body().jsonPath().getString("nickname")).isEqualTo(newNickName),
                () -> assertThat(수정후_로그인_응답.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }
}
