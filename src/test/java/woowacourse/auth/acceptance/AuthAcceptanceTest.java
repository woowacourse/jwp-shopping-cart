package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.TokenFixture.BEARER;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest.UserNameAndPassword;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ErrorResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceAuthTest {

    @DisplayName("등록된 회원이 로그인을 하면 토큰을 발급받는다.")
    @Test
    void loginReturnBearerToken() {
        // given
        UserNameAndPassword request = new UserNameAndPassword("giron", plainBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        final TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        final ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final TokenResponse tokenResponse = extract.as(TokenResponse.class);

        // then
        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }

    @DisplayName("Bearer Auth 로그인 성공하고, 내 정보 조회를 요청하면 내 정보가 조회된다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        UserNameAndPassword request = new UserNameAndPassword("giron", plainBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        final TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        final ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final TokenResponse tokenResponse = extract.as(TokenResponse.class);

        // when
        final ExtractableResponse<Response> responseMe = RestAssured
                .given().log().all()
                .header("Authorization", BEARER + tokenResponse.getAccessToken())
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
        final CustomerResponse customerResponse = responseMe.as(CustomerResponse.class);

        // then
        assertThat(customerResponse.getUserName()).isEqualTo("giron");
    }

    @DisplayName("Bearer Auth 로그인 실패 - 유저 이름이 잘못된 경우 404-NOT_FOUND를 반환한다.")
    @Test
    void loginFailureWithWrongUserName() {
        // given
        UserNameAndPassword request = new UserNameAndPassword("giron", plainBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        final TokenRequest tokenRequest = new TokenRequest("티키", plainBasicPassword);
        final ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = extract.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(extract.header(HttpHeaders.AUTHORIZATION)).isNull(),
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("존재하지 않는 유저입니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 실패 - 비밀번호가 잘못된 경우 404-NOT_FOUND를 반환한다.")
    @Test
    void loginFailureWithWrongPassword() {
        // given
        UserNameAndPassword request = new UserNameAndPassword("giron", plainBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        final TokenRequest tokenRequest = new TokenRequest("giron", "wrongPassword");
        final ExtractableResponse<Response> extract = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = extract.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(extract.header(HttpHeaders.AUTHORIZATION)).isNull(),
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰으로 접근했을 때 - 401 UNAUTHORIZED를 반환한다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        UserNameAndPassword request = new UserNameAndPassword("giron", plainBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        final ExtractableResponse<Response> responseMe = RestAssured
                .given().log().all()
                .header("Authorization", BEARER + "wrongAccessToken")
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = responseMe.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(responseMe.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("로그인할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void loginWithWrongUserName(String userName) {
        // given
        TokenRequest tokenRequest = new TokenRequest(userName, plainBasicPassword);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름은 빈칸일 수 없습니다.")
        );
    }

    @DisplayName("로그인할 때 바밀번호가 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void loginWithWrongPassword(String password) {
        // given
        TokenRequest tokenRequest = new TokenRequest("giron", password);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호는 빈칸일 수 없습니다.")
        );
    }

    @DisplayName("인증이 필요할 때, Authorization 헤더가 없다면, 401-UNAUTHORIZED를 반환한다.")
    @Test
    void withoutAuthorizationHeader() {
        // when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("로그인이 필요합니다.")
        );
    }
}
