package cart.web.auth;

import cart.domain.TestFixture;
import cart.service.user.UserRepository;
import cart.exception.AuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("유저의 정보를 통해 권한 인증을 수행한다.")
    @Test
    void authenticate() {
        userRepository.save(TestFixture.ZUNY);

        UserInfo savedUserInfo = new UserInfo(TestFixture.ZUNY.getEmail(), TestFixture.ZUNY.getPassword());
        UserInfo notSavedUserInfo = new UserInfo(TestFixture.ADMIN.getEmail(), TestFixture.ADMIN.getPassword());

        assertDoesNotThrow(() -> authService.authenticate(savedUserInfo));
        assertThatThrownBy(() -> authService.authenticate(notSavedUserInfo))
                .isInstanceOf(AuthorizationException.class);
    }
}
