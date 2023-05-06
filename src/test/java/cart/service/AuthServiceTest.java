package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

import cart.domain.user.User;
import cart.dto.BasicCredentials;
import cart.exception.InvalidPasswordException;
import cart.exception.UserNotFoundException;
import cart.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("유저 검증 기능")
    class AuthorizeUser {

        @Test
        @DisplayName("유저를 검증할 수 있다.")
        void authorize_user_success() {
            //given
            String email = "a@a.com";
            String password = "password";
            given(userRepository.findByEmail(email)).willReturn(Optional.of(new User(email, password)));

            //when
            //then
            BasicCredentials basicCredentials = new BasicCredentials(email, password);
            assertDoesNotThrow(() -> authService.authenticateUser(basicCredentials));
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는 경우 예외를 반환한다.")
        void authorize_user_fail() {
            //given
            String email = "a@a.com";
            String password = "password";
            given(userRepository.findByEmail(email)).willReturn(Optional.of(new User(email, "original")));

            //when
            BasicCredentials basicCredentials = new BasicCredentials(email, password);

            //then
            assertThatThrownBy(() -> authService.authenticateUser(basicCredentials))
                    .isInstanceOf(InvalidPasswordException.class);
        }

        @Test
        @DisplayName("회원이 존재하지 않을 경우 예외를 반환한다.")
        void authorize_user_fail_when_user_not_found() {
            //given
            String email = "a@a.com";
            String password = "password";
            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            //when
            BasicCredentials basicCredentials = new BasicCredentials(email, password);

            //then
            assertThatThrownBy(() -> authService.authenticateUser(basicCredentials))
                    .isInstanceOf(UserNotFoundException.class);
        }

    }
}
