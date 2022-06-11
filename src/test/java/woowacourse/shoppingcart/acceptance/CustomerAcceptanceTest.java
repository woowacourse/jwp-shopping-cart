package woowacourse.shoppingcart.acceptance;

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
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DuplicateResponse;
import woowacourse.shoppingcart.dto.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.RAW_BASIC_PASSWORD;
import static woowacourse.fixture.TokenFixture.BEARER;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입 성공하면 201 CREATED를 반환한다.")
    @Test
    void addCustomer() {
        // when
        ExtractableResponse<Response> response = 회원가입을_한다("giron", RAW_BASIC_PASSWORD);

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
        회원가입을_한다("giron", RAW_BASIC_PASSWORD);
        final TokenResponse tokenResponse = 로그인을_한다("giron", RAW_BASIC_PASSWORD).as(TokenResponse.class);

        // when
        ExtractableResponse<Response> extractableResponse = 내_정보를_조회한다(tokenResponse.getAccessToken());

        final CustomerResponse response = extractableResponse.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.getUserName()).isEqualTo("giron")
        );
    }

    @DisplayName("로그인 한 유저가 내 정보 수정하면 200 OK를 반환한다.")
    @Test
    void updateMe() {
        // given
        회원가입을_한다("giron", RAW_BASIC_PASSWORD);

        final TokenResponse tokenResponse = 로그인을_한다("giron", RAW_BASIC_PASSWORD).as(TokenResponse.class);
        // when
        CustomerRequest request =
                new CustomerRequest("giron", "8@76!432Aa1");

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
                () -> assertThat(response.getUserName()).isEqualTo("giron")
        );
    }

    @DisplayName("로그인 한 유저가 회원탈퇴를 하면 204 NO_CONTENT를 반환한다.")
    @Test
    void deleteMe() {
        // given
        회원가입을_한다("giron", RAW_BASIC_PASSWORD);

        final TokenResponse tokenResponse = 로그인을_한다("giron", RAW_BASIC_PASSWORD).as(TokenResponse.class);

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponse.getAccessToken())
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();
        ExtractableResponse<Response> findResponse = 내_정보를_조회한다(tokenResponse.getAccessToken());

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
        ExtractableResponse<Response> extractableResponse = 내_정보를_조회한다(invalidToken);
        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("중복된 이름이 있는지 확인하여 200-OK를 반환한다.")
    @Test
    void isDuplicatedUserName() {
        // given
        final String userName = "giron";
        회원가입을_한다(userName, RAW_BASIC_PASSWORD);

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("userName", userName)
                .body(userName)
                .when().get("/api/customers/exists")
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
    @ValueSource(strings = {"1234567", "", "1", "1234567qwerttyasdffgxzc"})
    void signUpWithShortPassword(String password) {
        // when
        final ExtractableResponse<Response> response = 회원가입을_한다("giron", password);

        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호는 8~16자의 영문 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자 1개 이상이어야 합니다. : " + password)
        );
    }

    @DisplayName("회원 가입할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "", " ", "123456678zuduihsfnsdfda"})
    void signUpWithWrongUserName(String userName) {

        // when
        final ExtractableResponse<Response> response = 회원가입을_한다(userName, "@Aa23445678");

        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름의 길이는 5이상 20이하여야 합니다. : " + userName)
        );
    }

    @DisplayName("회원 가입할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    void signUpWithNullUserName() {

        // when
        final ExtractableResponse<Response> response = 회원가입을_한다(null, "@Aa23445678");

        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름의 길이는 5이상 20이하여야 합니다. : ")
        );
    }
}
