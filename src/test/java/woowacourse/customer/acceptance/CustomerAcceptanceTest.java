package woowacourse.customer.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.customer.dto.CustomerResponse;
import woowacourse.customer.dto.PasswordConfirmRequest;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.customer.dto.UpdateCustomerRequest;
import woowacourse.customer.dto.UpdatePasswordRequest;
import woowacourse.exception.dto.ExceptionResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private final String username = "username";
    private final String password = "password1234";

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, "01001012323", "인천 서구 검단로");

        // when
        final ExtractableResponse<Response> response = 회원_가입_요청(signupRequest);

        // then
        회원_가입됨(response);
    }

    @DisplayName("회원가입 시 username은 3자 이상 ~ 15자 이하로만 이루어져 있어야 된다.")
    @Test
    void validateUsernameLength() {
        // given
        final SignupRequest signupRequest = new SignupRequest("do", password, "01012123434", "인천 서구 검단로");

        // when
        final ExtractableResponse<Response> response = 회원_가입_요청(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
                .containsExactly("사용자 이름의 길이는 3자 이상 15자 이하여야 합니다.")
        );
    }

    @DisplayName("비밀번호는 8자 이상 ~ 20자 이하로만 이루어져 있어야 한다.")
    @Test
    void validateFields() {
        // given
        final SignupRequest signupRequest = new SignupRequest("domain", "a", "01011112222", "인천 서구");

        // when
        final ExtractableResponse<Response> response = 회원_가입_요청(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages()).contains("비밀번호의 길이는 8자 이상 20자 이하여야 합니다.")
        );
    }

    @DisplayName("회원가입 시 이미 존재하는 username을 사용하면 예외를 반환해야 한다.")
    @Test
    void validateUniqueUsername() {
        // given
        final SignupRequest signupRequest = new SignupRequest("jjang9", "password1234", "01012121212", "서울시 여러분");
        회원_가입_요청(signupRequest);

        // when
        final ExtractableResponse<Response> response = 회원_가입_요청(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
                .containsExactly("이미 존재하는 사용자 이름입니다.")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, "01022728572", "인천 서구 검단로");
        회원_가입_요청(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = 로그인되어_토큰_가져옴(loginRequest);

        // when
        final CustomerResponse customerResponse = findCustomerInfo(accessToken);

        // then
        assertAll(
            () -> assertThat(customerResponse.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("비밀밀호가 일치하는지 확인한다.")
    @Test
    void confirmPassword() {
        final SignupRequest signupRequest = new SignupRequest(username, password, "01022728572", "인천 서구 검단로");
        회원_가입_요청(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = 로그인되어_토큰_가져옴(loginRequest);

        final PasswordConfirmRequest passwordConfirmRequest = new PasswordConfirmRequest(password);
        final ExtractableResponse<Response> response = 비밀번호_확인_요청(accessToken, passwordConfirmRequest);

        비밀번호_확인됨(response);
    }

    @DisplayName("phoneNumber, address 수정")
    @Test
    void updateMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, "01022728572", "인천 서구 검단로");
        회원_가입_요청(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = 로그인되어_토큰_가져옴(loginRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01011112222", "서울시 강남구");
        final ExtractableResponse<Response> response = 회원_정보_수정_요청(accessToken, updateCustomerRequest);

        final CustomerResponse customerResponse = findCustomerInfo(accessToken);

        assertAll(
            () -> 회원_정보_수정됨(response),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password 수정")
    @Test
    void updatePassword() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, "01022728572", "인천 서구 검단로");
        회원_가입_요청(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = 로그인되어_토큰_가져옴(loginRequest);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("password1234");
        final ExtractableResponse<Response> response = 비밀번호_변경_요청(accessToken, updatePasswordRequest);

        비밀번호_변경됨(response);
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest(username, password, "01022728572", "인천 서구 검단로");
        회원_가입_요청(signupRequest);

        final LoginRequest loginRequest = new LoginRequest(username, password);
        final String accessToken = 로그인되어_토큰_가져옴(loginRequest);

        final ExtractableResponse<Response> deleteResponse = 회원_탈퇴_요청(accessToken);
        final ExtractableResponse<Response> loginResponse = 로그인_요청(loginRequest);

        assertAll(
            () -> 회원_탈퇴됨(deleteResponse),
            () -> 존재하지_않는_계정으로_로그인_요청됨(loginResponse)
        );
    }

    public static ExtractableResponse<Response> 회원_가입_요청(final SignupRequest signupRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(signupRequest)
            .when().post("/api/customers/signup")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 비밀번호_확인_요청(final String accessToken, final PasswordConfirmRequest passwordConfirmRequest) {
        return RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .body(passwordConfirmRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/password")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_수정_요청(
        final String accessToken,
        final UpdateCustomerRequest updateCustomerRequest
    ) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(updateCustomerRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/api/customers")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 비밀번호_변경_요청(
        final String accessToken,
        final UpdatePasswordRequest updatePasswordRequest
    ) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(updatePasswordRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().patch("/api/customers/password")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴_요청(final String accessToken) {
        return RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/api/customers")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 로그인_요청(final LoginRequest loginRequest) {
        return RestAssured
            .given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all()
            .extract();
    }

    public static String 로그인되어_토큰_가져옴(final LoginRequest loginRequest) {
        return RestAssured
            .given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }

    private CustomerResponse findCustomerInfo(final String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers")
            .then().log().all()
            .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);
    }

    public static void 회원_가입됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 비밀번호_확인됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 회원_정보_수정됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 비밀번호_변경됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 회원_탈퇴됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 존재하지_않는_계정으로_로그인_요청됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
