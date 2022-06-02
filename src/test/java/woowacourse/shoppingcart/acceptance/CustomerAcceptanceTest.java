package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.rowBasicPassword;
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
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerRequest.UserNameOnly;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;
import woowacourse.shoppingcart.dto.ErrorResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입 성공하면 201 CREATED를 반환한다.")
    @Test
    void addCustomer() {
        // given
        CustomerRequest.UserNameAndPassword request = new CustomerRequest.UserNameAndPassword("기론",
                rowBasicPassword);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // then
        Long id = Long.parseLong(response.header("Location").split("/")[3]);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(id).isNotNull()
        );
    }

    @DisplayName("로그인 한 유저가 내 정보 조회하면 200 OK를 반환한다.")
    @Test
    void getMe() {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest = new CustomerRequest.UserNameAndPassword("기론",
                rowBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        final TokenRequest tokenRequest = new TokenRequest("기론", rowBasicPassword);
        final TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class);

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
        final CustomerResponse response = extractableResponse.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.getUserName()).isEqualTo("기론")
        );
    }

    @DisplayName("로그인 한 유저가 내 정보 수정하면 200 OK를 반환한다.")
    @Test
    void updateMe() {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest = new CustomerRequest.UserNameAndPassword("기론",
                rowBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        final TokenRequest tokenRequest = new TokenRequest("기론", rowBasicPassword);
        final TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class);

        // when
        CustomerRequest.UserNameAndPassword request = new CustomerRequest.UserNameAndPassword("기론", "87654321");
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/customers/me")
                .then().log().all()
                .extract();
        final CustomerResponse response = extractableResponse.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.getUserName()).isEqualTo("기론")
        );
    }

    @DisplayName("로그인 한 유저가 회원탈퇴를 하면 204 NO_CONTENT를 반환한다.")
    @Test
    void deleteMe() {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest = new CustomerRequest.UserNameAndPassword("기론",
                rowBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        final TokenRequest tokenRequest = new TokenRequest("기론", rowBasicPassword);
        final TokenResponse tokenResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .when().post("/api/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class);

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> findResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(findResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
        );
    }

    @DisplayName("유효하지않은 토큰을 담아서 요청을 보내면 401-UNAUTHORIZED를 반환한다.")
    @Test
    void getMeWithInvalidToken() {
        // given
        final String invalidToken = "invalidToken";

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + invalidToken)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("중복된 이름이 있는지 확인하여 200-OK를 반환한다.")
    @Test
    void isDuplicatedUserName() {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest = new CustomerRequest.UserNameAndPassword("기론",
                rowBasicPassword);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        // when
        CustomerRequest.UserNameOnly request = new UserNameOnly("기론");
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers/duplication")
                .then().log().all()
                .extract();
        final DuplicateResponse duplicateResponse = extractableResponse.as(DuplicateResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(duplicateResponse.getIsDuplicate()).isTrue()
        );
    }

    @DisplayName("회원 가입할 때 패스워드가 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234567", "", "1"})
    void signUpWithShortPassword(String password) {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest =
                new CustomerRequest.UserNameAndPassword("기론", password);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호는 8자리 이상이어야 합니다.")
        );
    }

    @DisplayName("회원 가입할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void signUpWithWrongUserName(String userName) {
        // given
        CustomerRequest.UserNameAndPassword signUpRequest =
                new CustomerRequest.UserNameAndPassword(userName, rowBasicPassword);

        // when
        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
        final ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름은 빈칸일 수 없습니다.")
        );
    }
}
