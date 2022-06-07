package woowacourse.auth.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setup() {
        post("/signup", 에덴);
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));

        // when
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);
        final ExtractableResponse<Response> response = get("/customers", token.getAccessToken());
        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getId()).isEqualTo(2L)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password1234!"));
        // then
        // 토큰 발급 요청이 거부된다
        assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        final ExtractableResponse<Response> tokenResponse = get("/customers", "invalidToken");
        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(tokenResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
