package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.request.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void addCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "123456");

        // when
        ExtractableResponse<Response> response = 회원가입(signUpRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보를 조회할 수 있다.")
    @Test
    void getMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // when
        ExtractableResponse<Response> response = 회원조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 조회할 수 없다.")
    @Test
    void getMeThrowException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        로그인(signInRequest);

        // when
        ExtractableResponse<Response> response = 회원조회(null);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("내 정보를 수정할 수 있다.")
    @Test
    void updateMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // when
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("123456", "567890");
        ExtractableResponse<Response> response = 회원수정(updatePasswordRequest, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 수정할 수 없다")
    @Test
    void updateMeThrowException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        로그인(signInRequest);

        // when
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("123456", "5678");
        ExtractableResponse<Response> response = 회원수정(updatePasswordRequest, null);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원을 탈퇴할 수 있다.")
    @Test
    void deleteMe() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // when
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456");
        ExtractableResponse<Response> response = 회원탈퇴(deleteCustomerRequest, token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 탈퇴할 수 없다")
    @Test
    void deleteMeThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        로그인(signInRequest);

        // when
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456");
        ExtractableResponse<Response> response = 회원탈퇴(deleteCustomerRequest, null);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

    public static ExtractableResponse<Response> 회원가입(SignUpRequest signUpRequest) {
        return RestAssured
                .given().log().all()
                .body(signUpRequest)
                .contentType(ContentType.JSON)
                .when().post("/users")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인(SignInRequest signInRequest) {
        return RestAssured
                .given().log().all()
                .body(signInRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원조회(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/users/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원수정(UpdatePasswordRequest updatePasswordRequest, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(updatePasswordRequest)
                .contentType(ContentType.JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/users/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴(DeleteCustomerRequest deleteCustomerRequest, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(deleteCustomerRequest)
                .contentType(ContentType.JSON)
                .when().delete("/users/me")
                .then().log().all()
                .extract();
    }
}
