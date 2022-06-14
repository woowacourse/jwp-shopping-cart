package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.nickname;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtils.httpPost;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        httpPost("/customers", signupRequest);

        // when
        ExtractableResponse<Response> loginResponse = httpPost("/auth/login", tokenRequest);

        // then
        assertAll(
                () -> assertThat(loginResponse.jsonPath().getString("nickname")).isEqualTo(nickname),
                () -> assertThat(loginResponse.jsonPath().getString("accessToken")).isNotNull()
        );
    }

    @DisplayName("로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        httpPost("/customers", signupRequest);

        // when
        TokenRequest tokenRequest = new TokenRequest(email, "a1234!!!23");
        ExtractableResponse<Response> loginResponse = httpPost("/auth/login", tokenRequest);

        // then
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
