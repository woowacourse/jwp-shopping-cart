package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private StubUserRepository stubUserRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        stubUserRepository = new StubUserRepository();
        authService = new AuthService(stubUserRepository);
    }

    @Test
    void 유효한_로그인_테스트() {
        assertThat(authService.isValidLogin("a@a.com", "password1")).isTrue();
    }

    @Test
    void 유효하지_않은_로그인_테스트() {
        assertThat(authService.isValidLogin("a@a.com", "password2")).isFalse();
    }
}
