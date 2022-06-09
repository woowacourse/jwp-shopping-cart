package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.deleteMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.patchMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.ChangeGeneralInfoRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private final String email = "test@gmail.com";
    private final String password = "password0!";
    private final String username = "루나";

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // when
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        final ExtractableResponse<Response> response = postMethodRequest(customerRequest,
                "/api/customers");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("location")).isEqualTo("/login")
        );
    }

    @DisplayName("회원가입 시 이메일이 이미 존재할 경우 에러를 발생한다.")
    @Test
    void checkDuplicatedEmail() {
        // given
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        // when
        final ExtractableResponse<Response> response = postMethodRequest(customerRequest,
                "/api/customers");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(ErrorResponse.DUPLICATED_EMAIL.getErrorCode()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(ErrorResponse.DUPLICATED_EMAIL.getMessage())
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");
        final String token = tokenResponse.jsonPath().getString("accessToken");

        // when
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token,
                "/api/customers/me");
        final CustomerResponse customerResponse = response.jsonPath()
                .getObject(".", CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(username),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("비밀번호 수정")
    @Test
    void updatePassword() {
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest, "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(password,
                "newPwd0!");
        final ExtractableResponse<Response> response = patchMethodRequestWithBearerAuth(
                changePasswordRequest, token,
                "/api/customers/me?target=password");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header("Location")).isEqualTo("/login")
        );
    }

    @DisplayName("회원 일반 정보 수정")
    @Test
    void updateGeneralInformation() {
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final ChangeGeneralInfoRequest changeGeneralInfoRequest = new ChangeGeneralInfoRequest(
                "루나2");
        final ExtractableResponse<Response> response = patchMethodRequestWithBearerAuth(
                changeGeneralInfoRequest, token,
                "/api/customers/me?target=generalInfo");
        final CustomerResponse customerResponse = response.jsonPath()
                .getObject(".", CustomerResponse.class);

        final ExtractableResponse<Response> responseAfterChanged = getMethodRequestWithBearerAuth(
                token,
                "/api/customers/me");
        final String modifiedUsername = responseAfterChanged.jsonPath().getString("username");

        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getUsername()).isEqualTo("루나2"),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(modifiedUsername).isEqualTo("루나2")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(password);
        final ExtractableResponse<Response> response = deleteMethodRequestWithBearerAuth(
                deleteCustomerRequest, token,
                "/api/customers/me");

        final ExtractableResponse<Response> responseAfterDeleted = postMethodRequest(loginRequest,
                "/api/auth/login");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(response.header("Location")).isEqualTo("/"),
                () -> assertThat(responseAfterDeleted.statusCode()).isEqualTo(
                        HttpStatus.BAD_REQUEST.value())
        );
    }
}
