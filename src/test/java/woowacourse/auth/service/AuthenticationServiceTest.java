package woowacourse.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.exception.unauthorized.LoginFailureException;

@SpringBootTest
@Sql({"/truncate.sql", "/auth.sql"})
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @DisplayName("로그인 성공시 토큰을 발급한다.")
    @Test
    void getToken() {
        // given
        final TokenRequest 로그인요청 = 잉_로그인요청;

        // when
        final String 발급된토큰 = authenticationService.getToken(로그인요청);

        // then
        assertThat(발급된토큰).isNotBlank();
    }

    @DisplayName("로그인시 비밀번호가 일치하지 않으면 예외를 발생한다.")
    @Test
    void getTokenWithPasswordInCorrectException() {
        // given
        final TokenRequest 로그인요청 = new TokenRequest(잉_로그인요청.getEmail(), 잉_로그인요청.getPassword() + "stranger");

        // when then
        assertThatThrownBy(() -> authenticationService.getToken(로그인요청))
                .isInstanceOf(LoginFailureException.class);
    }
}
