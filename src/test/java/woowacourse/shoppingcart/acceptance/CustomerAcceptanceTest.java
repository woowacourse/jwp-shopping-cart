package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.ExceptionResponse;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01001012323", "인천 서구 검단로");

        // when
        final ExtractableResponse<Response> response = signup(signupRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입 시 username은 3자 이상 ~ 15자 이하로만 이루어져 있어야 된다.")
    @Test
    void validateUsernameLength() {
        // given
        final SignupRequest signupRequest = new SignupRequest("do", "ehdgh1234", "01012123434", "인천 서구 검단로");

        // when
        final ExtractableResponse<Response> response = signup(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
                .containsExactly("username의 길이는 3자 이상 15자 이하여야 합니다.")
        );
    }

    @DisplayName("여러 필드의 검증이 실패한경우 에러메세지를 모두 리스트로 담아 보내야 한다.")
    @Test
    void validateFields() {
        // given
        final SignupRequest signupRequest = new SignupRequest("do", "a", "1", "인천 서구");

        // when
        final ExtractableResponse<Response> response = signup(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(3)
        );
    }

    @DisplayName("회원가입 시 이미 존재하는 username을 사용하면 예외를 반환해야 한다.")
    @Test
    void validateUniqueUsername() {
        // given
        final SignupRequest signupRequest = new SignupRequest("jjang9", "password1234", "01012121212", "서울시 여러분");
        signup(signupRequest);

        // when
        final ExtractableResponse<Response> response = signup(signupRequest);
        final ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
                .containsExactly("이미 존재하는 username입니다.")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        signup(signupRequest);

        final LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        final String accessToken = login(loginRequest);

        // when
        final CustomerResponse customerResponse = findCustomerInfo(accessToken);

        // then
        assertAll(
            () -> assertThat(customerResponse.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("phoneNumber, address 수정")
    @Test
    void updateMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        signup(signupRequest);

        final LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        final String accessToken = login(loginRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01011112222", "서울시 강남구");
        ValidatableResponse validatableResponse = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(updateCustomerRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/api/customers")
            .then().log().all();

        final CustomerResponse customerResponse = findCustomerInfo(accessToken);

        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.NO_CONTENT.value()),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password 수정")
    @Test
    void updatePassword() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        signup(signupRequest);

        final LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        final String accessToken = login(loginRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("password1234");
        RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(updateCustomerRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().patch("/api/customers/password")
            .then().log().all().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        final SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        signup(signupRequest);

        final LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        final String accessToken = login(loginRequest);

        RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/api/customers")
            .then().log().all()
            .extract();

        RestAssured
            .given().log().all()
            .body(new LoginRequest("dongho108", "ehdgh1234"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> signup(final SignupRequest signupRequest) {
        return RestAssured.given().log().all()
            .body(signupRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/customers/signup")
            .then().log().all()
            .extract();
    }

    private String login(final LoginRequest loginRequest) {
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
}
