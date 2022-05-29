package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.TMember.MARU;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("올바른 email, password를 입력할 경우 토큰을 헤더로 반환하고 200 OK를 반환한다.")
    @Test
    void login() {
        TokenResponse response = MARU.registerAnd().login();
        assertThat(response.getAccessToken()).isNotNull();
    }

    @DisplayName("틀린 비밀번호를 입력할 경우 400 badRequest와 에러메시지를 반환한다.")
    @Test
    void loginFailed() {
        ErrorResponse errorResponse = MARU.registerAnd().failedLogin("Wrongpw1234!");
        assertThat(errorResponse.getMessage()).isEqualTo("[ERROR] 비밀번호가 틀렸습니다.");
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
