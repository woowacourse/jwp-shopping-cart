package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.support.SimpleRestAssured;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final String username = "my-username";
        CustomerAcceptanceFixture.saveCustomerWithName(username);
        TokenRequest request = new TokenRequest(username, "password12!@");

        TokenResponse tokenResponse = SimpleRestAssured.toObject(
            SimpleRestAssured.post("/api/auth/token", request),
            TokenResponse.class
        );

        // when
        String accessToken = AuthorizationExtractor.BEARER_TYPE + tokenResponse.getAccessToken();
        CustomerResponse customerResponse = SimpleRestAssured.toObject(
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", accessToken)),
            CustomerResponse.class
        );

        // then
        assertThat(customerResponse.getUsername()).isEqualTo("my-username");
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        CustomerAcceptanceFixture.saveCustomer();

        // when
        TokenRequest request = new TokenRequest("username", "wrongPassword12@");
        ExtractableResponse<Response> tokenResponse =
            SimpleRestAssured.post("/api/auth/token", request);

        // then
        assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        CustomerAcceptanceFixture.saveCustomer();

        // when
        String invalidToken = "Bearer aaaaaa.bbbbbb.ccccc";
        ExtractableResponse<Response> authResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", invalidToken));

        // then
        assertThat(authResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
