package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("회원 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters", "InnerClassMayBeStatic"})
class AuthControllerTest extends AcceptanceTest {

    @Test
    void 로그인() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        회원이_저장되어_있음(account, password);

        ExtractableResponse<Response> response = 토큰_발급(account, password);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(getValue(response, "accessToken")).isNotNull();
    }

    @Test
    void 비밀번호_불일치_로그인_실패() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        회원이_저장되어_있음(account, password);

        ExtractableResponse<Response> response = 토큰_발급(account, "dpepsWkd");

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(getValue(response, "message")).contains("로그인이 불가능합니다.");
    }
}
