package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;

@SpringBootTest
@Sql("classpath:resetTables.sql")
class AuthControllerTest {

    private static final String NAME = "klay";
    private static final String EMAIL = "klay@gmail.com";
    private static final String PASSWORD = "12345678";

    @Autowired
    private AuthController authController;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("이메일과 비밀번호를 받아 로그인한다.")
    void login() {
        // given
        final TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        customerService.save(new CustomerSaveServiceRequest(NAME, EMAIL, PASSWORD));

        // when
        final TokenResponse tokenResponse = authController.login(tokenRequest);
        final boolean actual = jwtTokenProvider.validateToken(tokenResponse.getAccessToken());

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 요청 시 예외가 발생한다.")
    void login_invalidEmail_throwsEmail() {
        // given
        final TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);

        // when, then
        assertThatThrownBy(() -> authController.login(tokenRequest))
                .isInstanceOf(NoSuchEmailException.class);
    }

    @Test
    @DisplayName("로그인시 비밀번호가 일치하지 않는 경우 예외가 발생한다.")
    void login_passwordNotMatch_throwsException() {
        // given
        customerService.save(new CustomerSaveServiceRequest(NAME, EMAIL, PASSWORD));

        // when, then
        final TokenRequest tokenRequest = new TokenRequest(EMAIL, "1213123213123212");
        assertThatThrownBy(() -> authController.login(tokenRequest))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
