package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.user.User;
import cart.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

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
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE=");
        assertThat(authService.getUser(request)).isEqualTo(new User("a@a.com", "password1"));
    }

    @Test
    void 유효하지_않은_로그인_테스트() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic YkBhLmNvbTpwYXNzd29yZDI=");
        assertThatThrownBy(() -> authService.getUser(request))
                .isInstanceOf(LoginFailException.class);
    }
}
