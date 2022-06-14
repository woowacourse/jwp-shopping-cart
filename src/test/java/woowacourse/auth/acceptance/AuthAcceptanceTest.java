package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.stream.Stream;
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

    @DisplayName("회원정보 양식이 잘못됐을 때 400에러를 응답한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidLoginForm")
    void invalidLoginFormatRequest(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(INVALID_FORMAT_ERROR_CODE),
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
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(INVALID_LOGIN_ERROR_CODE),
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
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(INVALID_LOGIN_ERROR_CODE),
                () -> assertThat(response.jsonPath().getString("message")).isNotBlank()
        );
    }

    @DisplayName("정상 케이스일 때 토큰과 함께 200을 응답")
    @Test
    void validCustomerLoginRequest() {
        회원가입_요청("email@email.com", "12345678a", "tonic");

        RestAssured
            .given(spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(Map.of("email", "email@email.com", "password", "12345678a"))
            .filter(document("login",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Content-Type").description("컨텐츠 타입")
                ),
                requestFields(
                    fieldWithPath("email").description("사용자 이메일"),
                    fieldWithPath("password").description("사용자 비밀번호")
                ),
                responseFields(
                    fieldWithPath("accessToken").description("사용자 JWT 토큰")
                )
            ))
            .when().log().all()
            .post("/login")
            .then().log().all()
            .assertThat().statusCode(HttpStatus.OK.value())
            .assertThat().body("accessToken", not(blankString()));
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
