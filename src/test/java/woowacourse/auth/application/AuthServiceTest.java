package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.LoginException;
import woowacourse.shoppingcart.customer.application.CustomerService;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    private static final String EMAIL = "east@gmail.com";
    private static final String PASSWORD = "password1!";
    private static final String USER_NAME = "이스트";

    @DisplayName("로그인 기능 정상 동작 확인")
    @Test
    void login() {
        //given
        customerService.register(EMAIL, PASSWORD, USER_NAME);
        //when
        final TokenResponse tokenResponse = authService.login(EMAIL, PASSWORD);
        //then
        assertThat(tokenResponse).isNotNull();
    }

    @DisplayName("로그인시 비밀번호가 일치하지 않으면 예외 발생")
    @Test
    void loginWithIncorrectPassword() {
        //given
        customerService.register(EMAIL, PASSWORD, USER_NAME);
        //then
        assertThatThrownBy(() -> authService.login(EMAIL, "password2!"))
                .isInstanceOf(LoginException.class);
    }
}
