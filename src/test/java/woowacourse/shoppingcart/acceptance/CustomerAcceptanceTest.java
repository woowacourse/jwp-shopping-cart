package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerDeletionRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String BEARER = "Bearer ";
    private static final int DUPLICATE_EMAIL = 1001;
    private static final int INCORRECT_PASSWORD = 3001;

    @DisplayName("email, password, username 을 통해서 회원가입을 할 수 있다.")
    @Test
    void addCustomer() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");

        // when
        final ExtractableResponse<Response> signUpResponse = AcceptanceFixture.post(customerRequest, "/api/customers");

        // then
        assertThat(signUpResponse.statusCode()).isEqualTo(CREATED.value());
        assertThat(signUpResponse.header("Location")).isNotBlank().isEqualTo("/login");
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password1!");
        final ExtractableResponse<Response> loginResponse =
            AcceptanceFixture.post(tokenRequest, "/api/auth/login");
        final String accessToken = extractAccessToken(loginResponse);

        // when
        Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> myInfoResponse = AcceptanceFixture.get("/api/customers/me", header);

        final CustomerResponse customerResponse = extractCustomer(myInfoResponse);

        // then
        assertThat(myInfoResponse.statusCode()).isEqualTo(OK.value());
        assertThat(customerResponse)
            .extracting("email", "username")
            .containsExactly(customerRequest.getEmail(), customerRequest.getUsername());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password1!");
        final ExtractableResponse<Response> loginResponse =
            AcceptanceFixture.post(tokenRequest, "/api/auth/login");
        final String accessToken = extractAccessToken(loginResponse);

        // when
        final CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("dwoo");
        Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> updateResponse =
            AcceptanceFixture.patch(updateRequest, "/api/customers/me?target=generalInfo", header);

        final CustomerResponse customerResponse = extractCustomer(updateResponse);

        // then
        assertThat(updateResponse.statusCode()).isEqualTo(OK.value());
        assertThat(customerResponse)
            .extracting("email", "username")
            .containsExactly(customerRequest.getEmail(), updateRequest.getUsername());
    }

    @DisplayName("내 비밀번호 변경")
    @Test
    void changePassword() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password1!");
        final ExtractableResponse<Response> loginResponse =
            AcceptanceFixture.post(tokenRequest, "/api/auth/login");
        final String accessToken = extractAccessToken(loginResponse);

        // when
        final ChangePasswordRequest changePasswordRequest =
            new ChangePasswordRequest(customerRequest.getPassword(), "newpwd1!");
        Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> changePasswordResponse =
            AcceptanceFixture.patch(changePasswordRequest, "/api/customers/me?target=password", header);

        // then
        assertThat(changePasswordResponse.statusCode()).isEqualTo(OK.value());
        assertThat(changePasswordResponse.header("Location")).isNotBlank().isEqualTo("/login");
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password1!");
        final ExtractableResponse<Response> loginResponse =
            AcceptanceFixture.post(tokenRequest, "/api/auth/login");
        final String accessToken = extractAccessToken(loginResponse);

        // when
        final CustomerDeletionRequest customerDeletionRequest =
            new CustomerDeletionRequest(customerRequest.getPassword());
        Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> customerDeletionResponse =
            AcceptanceFixture.delete(customerDeletionRequest, "/api/customers/me", header);

        // then
        assertThat(customerDeletionResponse.statusCode()).isEqualTo(NO_CONTENT.value());
        assertThat(customerDeletionResponse.header("Location")).isNotBlank().isEqualTo("/");
    }

    @DisplayName("이미 등록되어 있는 이메일로는 회원가입이 불가능하다.")
    @Test
    public void duplicateEmail() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        // when
        final ExtractableResponse<Response> response = AcceptanceFixture.post(customerRequest, "/api/customers");

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(extractErrorCode(response)).isEqualTo(DUPLICATE_EMAIL);
    }

    @DisplayName("잘못된 형식으로 회원가입 요청을 한 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @CsvSource(value = {"invalidemail, password1!, azpi, 4001",
        "email@email.com, invalidpwd, azpi, 4002",
        "email@email.com, password1!, invalidusername, 4003"})
    public void invalidEmail(String email, String password, String username, int errorCode) {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest(email, password, username);

        // when
        final ExtractableResponse<Response> signUpResponse = AcceptanceFixture.post(customerRequest, "/api/customers");

        // then
        assertThat(signUpResponse.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(extractErrorCode(signUpResponse)).isEqualTo(errorCode);
    }

    @DisplayName("기존 패스워드와 불일치하는 경우 비밀번호 변경이 불가능하다.")
    @Test
    public void invalidChangePassword() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "azpi");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest("email@email.com", "password1!");
        final ExtractableResponse<Response> loginResponse =
            AcceptanceFixture.post(tokenRequest, "/api/auth/login");
        final String accessToken = extractAccessToken(loginResponse);

        // when
        final ChangePasswordRequest changePasswordRequest =
            new ChangePasswordRequest("incorrect1!", "newpwd1!");
        Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> changePasswordResponse =
            AcceptanceFixture.patch(changePasswordRequest, "/api/customers/me?target=password", header);

        // then
        assertThat(changePasswordResponse.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(extractErrorCode(changePasswordResponse)).isEqualTo(INCORRECT_PASSWORD);
    }

    private String extractAccessToken(ExtractableResponse<Response> response) {
        return response.jsonPath()
            .getObject(".", TokenResponse.class)
            .getAccessToken();
    }

    private CustomerResponse extractCustomer(ExtractableResponse<Response> response) {
        return response.jsonPath()
            .getObject(".", CustomerResponse.class);
    }

    private int extractErrorCode(ExtractableResponse<Response> response) {
        return response.jsonPath().getInt("errorCode");
    }
}
