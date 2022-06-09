package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import woowacourse.auth.dto.CustomerDto;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.shoppingcart.dto.ExceptionResponse;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        ExtractableResponse<Response> response = 회원가입_되어있음(signupRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("중복된 username으로 회원가입시 badRequest를 반환해야 한다.")
    @Test
    void addDuplicatedCustomer() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        회원가입_되어있음(signupRequest);

        // when
        SignupRequest duplicatedUsernameSignupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // then
        회원가입_되어있음(duplicatedUsernameSignupRequest);
    }

    @DisplayName("회원가입 시 username은 3자 이상 ~ 15자 이하로만 이루어져 있어야 된다.")
    @Test
    void validateUsernameLength() {
        // given
        SignupRequest signupRequest = new SignupRequest("do", "ehdgh1234", "01022728572", "인천 서구 검단로 851 동부아파트 108동 303호");

        // when
        ExtractableResponse<Response> response = 회원가입_되어있음(signupRequest);
        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(1)
        );
    }

    @DisplayName("여러 필드의 검증이 실패한경우 에러메세지를 모두 리스트로 담아 보내야 한다.")
    @Test
    void validateFields() {
        // given
        SignupRequest signupRequest = new SignupRequest("do", "a", "1", "인천 서구 검단로 851 동부아파트 108동 303호");

        // when
        ExtractableResponse<Response> response = 회원가입_되어있음(signupRequest);
        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(exceptionResponse.getMessages())
                .hasSize(3)
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        회원가입_되어있음(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        String accessToken = 로그인하고_토큰받아옴(loginRequest);

        // when
        CustomerResponse customerResponse = 내_정보_조회해옴(accessToken);
        CustomerDto customerDto = customerResponse.getCustomerDto();

        // then
        assertAll(
            () -> assertThat(customerDto.getUsername()).isEqualTo(signupRequest.getUsername()),
            () -> assertThat(customerDto.getPhoneNumber()).isEqualTo(signupRequest.getPhoneNumber()),
            () -> assertThat(customerDto.getAddress()).isEqualTo(signupRequest.getAddress())
        );
    }

    @DisplayName("phoneNumber, address 수정")
    @Test
    void updateMe() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        회원가입_되어있음(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        String accessToken = 로그인하고_토큰받아옴(loginRequest);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("01011112222", "서울시 강남구");
        ValidatableResponse validatableResponse = 내_정보_수정함(accessToken, updateCustomerRequest);

        CustomerResponse customerResponse = 내_정보_조회해옴(accessToken);
        CustomerDto customerDto = customerResponse.getCustomerDto();

        assertAll(
            () -> validatableResponse.statusCode(HttpStatus.NO_CONTENT.value()),
            () -> assertThat(customerDto.getPhoneNumber()).isEqualTo(updateCustomerRequest.getPhoneNumber()),
            () -> assertThat(customerDto.getAddress()).isEqualTo(updateCustomerRequest.getAddress())
        );
    }

    @DisplayName("password 수정")
    @Test
    void updatePassword() {
        // given
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        회원가입_되어있음(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        String accessToken = 로그인하고_토큰받아옴(loginRequest);

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
        SignupRequest signupRequest = new SignupRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        회원가입_되어있음(signupRequest);

        LoginRequest loginRequest = new LoginRequest("dongho108", "ehdgh1234");
        String accessToken = 로그인하고_토큰받아옴(loginRequest);

        회원_삭제함(accessToken);

        RestAssured
            .given().log().all()
            .body(loginRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/api/customers/login")
            .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void 회원_삭제함(String accessToken) {
        RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete("/api/customers")
            .then().log().all()
            .extract();
    }

    private CustomerResponse 내_정보_조회해옴(String accessToken) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers")
            .then().log().all()
            .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);
    }

    private ValidatableResponse 내_정보_수정함(String accessToken, UpdateCustomerRequest updateCustomerRequest) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .body(updateCustomerRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/api/customers")
            .then().log().all();
    }
}
