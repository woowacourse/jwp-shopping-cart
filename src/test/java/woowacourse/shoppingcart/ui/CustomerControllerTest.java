package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.nickname;
import static woowacourse.utils.Fixture.password;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtils.deleteWithToken;
import static woowacourse.utils.RestAssuredUtils.getWithToken;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;
import static woowacourse.utils.RestAssuredUtils.patchWithToken;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.customer.CustomerPasswordUpdateRequest;
import woowacourse.auth.dto.customer.CustomerProfileUpdateRequest;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.utils.AcceptanceTest;

class CustomerControllerTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp() {
        // given
        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
        );
    }

    @DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
    @Test
    void signUpDuplicatedEmail() throws Exception {
        // given
        ExtractableResponse<Response> firstResponse = httpPost("/customers", signupRequest);

        // given
        ExtractableResponse<Response> secondResponse = httpPost("/customers", signupRequest);

        assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일의 공백이면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void emailFormatException(String email) throws Exception {
        // given
        ExtractableResponse<Response> response = httpPost("/customers", new SignupRequest(email, password, nickname));

        // when && then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("닉네임의 형식이 올바르지 못하면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901"})
    void nicknameFormatException(String nickname) throws Exception {
        // given
        ExtractableResponse<Response> response = httpPost("/customers", new SignupRequest(email, password, nickname));

        // when && then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호의 형식이 올바르지 못하면 400 반환")
    @ParameterizedTest
    @ValueSource(strings = {"1234", "abasdas", "!@#!@#", "123d213", "asdasd!@#@", "123!@@##!1"})
    void passwordFormatException(String password) throws Exception {
        // given
        ExtractableResponse<Response> response = httpPost("/customers", new SignupRequest(email, password, nickname));

        // when && then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("토큰이 없을 때 탈퇴를 하려고 하면 401 반환")
    @Test
    void signOutNotLogin() throws Exception {
        // when
        SignoutRequest signoutRequest = new SignoutRequest(password);
        ExtractableResponse<Response> response = deleteWithToken("/customers", "noToken", signoutRequest);

        // when && then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰이 있고 비밀번호가 일치할 때 회원 탈퇴를 한다.")
    @Test
    void signOutwithToken_correct_password() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);

        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        SignoutRequest signoutRequest = new SignoutRequest(password);
        ExtractableResponse<Response> response = deleteWithToken("/customers", token, signoutRequest);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰이 있고 비밀번호가 일치하지 않을 때 회원 탈퇴를 하지 못한다.")
    @Test
    void signOutwithToken_incorrect_password() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);

        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        SignoutRequest signoutRequest = new SignoutRequest("incorrect!1");
        ExtractableResponse<Response> response = deleteWithToken("/customers", token, signoutRequest);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    @DisplayName("토큰이 있을 때 회원정보 수정을 한다.")
    @Test
    void updateCustomer() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);

        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        CustomerProfileUpdateRequest request = new CustomerProfileUpdateRequest("thor");

        ExtractableResponse<Response> response = patchWithToken("/customers/profile", token, request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없을 때 회원 정보 수정을 하면 401 반환")
    @Test
    void updateCustomerNotToken() {
        // given
        httpPost("/customers", signupRequest);

        // when
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "thor", "a1234!", "b1234!"
        );

        ExtractableResponse<Response> response = patchWithToken("/customers", "noToken", request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("기존 비밀번호가 다르면 정보를 수정할 수 없다.")
    @Test
    void updateCustomerNotSamePassword() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);

        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest("different!1", "b1234!");

        ExtractableResponse<Response> response = patchWithToken("/customers/password", token, request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원정보를 조회할 수 있다.")
    @Test
    void findCustomer() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);

        String token = loginResponse.jsonPath().getString("accessToken");

        ExtractableResponse<Response> response = getWithToken("/customers", token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
