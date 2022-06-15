package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.AcceptanceTestUtil.preflight_요청을_한다;
import static woowacourse.util.AcceptanceTestUtil.로그인을_한다;
import static woowacourse.util.AcceptanceTestUtil.비밀번호를_확인한다;
import static woowacourse.util.AcceptanceTestUtil.로그인_없이_비밀번호를_확인한다;
import static woowacourse.util.AcceptanceTestUtil.회원가입을_한다;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.response.ErrorResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원 가입에 성공하고 가입한 정보로 로그인을 하면 토큰과 닉네임을 반환한다.")
    @Test
    void signUp_Login_GetToken() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");

        ExtractableResponse<Response> response = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!");
        LoginResponse responseBody = response.as(LoginResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseBody.getToken()).isNotNull();
        assertThat(responseBody.getNickname()).isEqualTo("닉네임");
    }

    @DisplayName("로그인 한 뒤 비밀번호 검증을 다시 요청하면 비밀번호가 맞는 지 반환한다.")
    @Test
    void login_CheckPassword() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!")
                .as(LoginResponse.class)
                .getToken();

        ExtractableResponse<Response> response = 비밀번호를_확인한다(token, "1q2w3e4r!");
        boolean success = response.as(PasswordCheckResponse.class)
                .isSuccess();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(success).isTrue();
    }

    @DisplayName("로그인을 하지 않고(토큰이 없이) 비밀번호 확인을 요청할 수 없다.")
    @Test
    void requestWithUnauthorized() {
        ExtractableResponse<Response> response = 로그인_없이_비밀번호를_확인한다();
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("로그인이 필요합니다.");
    }

    @DisplayName("유효하지 않은 토큰으로 비밀번호를 확인을 요청하면 401을 응답한다.")
    @Test
    void requestWithInvalidToken_Unauthorized() {
        ExtractableResponse<Response> response = 비밀번호를_확인한다("invalid", "1q2w3e4r!");
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @DisplayName("preflight 요청에 대해서는 인증 정보를 확인하지 않는다.")
    @Test
    void preflight() {
        ExtractableResponse<Response> response = preflight_요청을_한다("/api/members/password-check");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
