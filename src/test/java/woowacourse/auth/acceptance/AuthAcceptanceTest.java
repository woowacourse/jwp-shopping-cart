package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
    }

    @DisplayName("회원정보 양식이 잘못됐을 때 400에러를 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidLoginForm")
    void invalidLoginFormatRequest(String email, String password) {
        // given
        // 회원이 등록되어 있고
        Map<String, String> body = Map.of(
                "email", email,
                "password", password
        );
        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
        // then
        // 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(1000),
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

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
