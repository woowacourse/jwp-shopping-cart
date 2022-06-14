package woowacourse.auth.acceptance;

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
import woowacourse.shoppingcart.acceptance.AcceptanceFixture;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    public static final String BEARER = "Bearer ";
    private static final int LOGIN_FAIL = 2001;
    private static final int INVALID_TOKEN = 3002;

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "dwoo");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest(customerRequest.getEmail(), customerRequest.getPassword());
        final ExtractableResponse<Response> loginResponse = AcceptanceFixture.post(tokenRequest, "/api/auth/login");

        // when
        final String accessToken = extractAccessToken(loginResponse);

        final Header header = new Header("Authorization", BEARER + accessToken);
        final ExtractableResponse<Response> myInfoResponse = AcceptanceFixture.get("/api/customers/me", header);
        final CustomerResponse customerResponse = extractCustomer(myInfoResponse);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(OK.value());
        assertThat(customerResponse)
            .extracting("email", "username")
            .containsExactly(customerRequest.getEmail(), customerRequest.getUsername());
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, invalidpwd2!", "invalidemail2@email.com, password1!"})
    void myInfoWithBadBearerAuth(String email, String password) {
        // given
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "dwoo");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        // when
        final TokenRequest tokenRequest = new TokenRequest(email, password);
        final ExtractableResponse<Response> loginResponse = AcceptanceFixture.post(tokenRequest, "/api/auth/login");

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(extractErrorCode(loginResponse)).isEqualTo(LOGIN_FAIL);
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        final Header header = new Header("Authorization", "Bearer header");
        final ExtractableResponse<Response> myInfoResponse = AcceptanceFixture.get("/api/customers/me", header);

        // then
        assertThat(myInfoResponse.statusCode()).isEqualTo(UNAUTHORIZED.value());
        assertThat(extractErrorCode(myInfoResponse)).isEqualTo(INVALID_TOKEN);
    }

    public static String extractAccessToken(ExtractableResponse<Response> response) {
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
