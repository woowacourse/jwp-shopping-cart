package cart.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.user.User;
import cart.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        final StubUserRepository stubUserRepository = new StubUserRepository();
        authService = new AuthService(stubUserRepository);
    }

    @Test
    void 유효한_로그인_테스트() {
        assertThatCode(() -> authService.validateLogin(new User("a@a.com", "password1")))
                .doesNotThrowAnyException();
    }

    @Test
    void 존재하지_않는_아이디_테스트() {
        final User user = new User("b@b.com", "password2");
        assertThatThrownBy(() -> authService.validateLogin(user))
                .isInstanceOf(EmailNotFoundException.class);
    }

    @Test
    void 패스워드_불일치_테스트() {
        final User user = new User("a@a.com", "password2");
        assertThatThrownBy(() -> authService.validateLogin(user))
                .isInstanceOf(PasswordMismatchException.class);
    }
}
