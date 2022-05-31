package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.AcceptanceTestFixture.getMethodRequestWithBearerAuth;
import static woowacourse.AcceptanceTestFixture.postMethodRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

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

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
