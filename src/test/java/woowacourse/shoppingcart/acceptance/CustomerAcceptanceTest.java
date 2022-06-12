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
import woowacourse.shoppingcart.customer.dto.ChangeGeneralInfoRequest;
import woowacourse.shoppingcart.customer.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.customer.dto.CustomerRequest;
import woowacourse.shoppingcart.customer.dto.CustomerResponse;
import woowacourse.shoppingcart.cartitem.dto.DeleteCustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        final CustomerRequest customerRequest = new CustomerRequest("test@gmail.com", "password0!", "루나");

        final ExtractableResponse<Response> response = postMethodRequest(customerRequest, "/api/customers");

        assertAll(
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                ()-> assertThat(response.header("location")).isEqualTo("/login")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final ExtractableResponse<Response> response = getMethodRequestWithBearerAuth(token,
                "/api/customers/me");

        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        assertAll(
                ()-> assertThat(customerResponse.getEmail()).isEqualTo(email),
                ()-> assertThat(customerResponse.getUsername()).isEqualTo(username),
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("비밀번호 수정")
    @Test
    void updatePassword() {
        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(password, "newPwd0!");
        final ExtractableResponse<Response> response = patchMethodRequestWithBearerAuth(changePasswordRequest, token,
                "/api/customers/me?target=password");

        assertAll(
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                ()-> assertThat(response.header("Location")).isEqualTo("/login")
        );
    }

    @DisplayName("회원 일반 정보 수정")
    @Test
    void updateGeneralInformation() {
        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final ChangeGeneralInfoRequest changeGeneralInfoRequest = new ChangeGeneralInfoRequest("루나2");
        final ExtractableResponse<Response> response = patchMethodRequestWithBearerAuth(changeGeneralInfoRequest, token,
                "/api/customers/me?target=generalInfo");
        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        final ExtractableResponse<Response> responseAfterChanged = getMethodRequestWithBearerAuth(token,
                "/api/customers/me");
        final String modifiedUsername = responseAfterChanged.jsonPath().getString("username");

        assertAll(
                ()-> assertThat(customerResponse.getEmail()).isEqualTo(email),
                ()-> assertThat(customerResponse.getUsername()).isEqualTo("루나2"),
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                ()-> assertThat(modifiedUsername).isEqualTo("루나2")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        final String email = "test@gmail.com";
        final String password = "password0!";
        final String username = "루나";
        final CustomerRequest customerRequest = new CustomerRequest(email, password, username);
        postMethodRequest(customerRequest, "/api/customers");

        final LoginRequest loginRequest = new LoginRequest(email, password);
        final ExtractableResponse<Response> tokenResponse = postMethodRequest(loginRequest,
                "/api/auth/login");

        final String token = tokenResponse.jsonPath().getString("accessToken");
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(password);
        final ExtractableResponse<Response> response = deleteMethodRequestWithBearerAuth(deleteCustomerRequest, token,
                "/api/customers/me");

        final ExtractableResponse<Response> responseAfterDeleted = postMethodRequest(loginRequest,
                "/api/auth/login");

        assertAll(
                ()-> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                ()-> assertThat(response.header("Location")).isEqualTo("/"),
                ()-> assertThat(responseAfterDeleted.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        );
    }
}
