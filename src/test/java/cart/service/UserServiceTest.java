package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

import cart.domain.User;
import cart.exception.InvalidPasswordException;
import cart.exception.UserNotFoundException;
import cart.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("모든 유저 정보를 반환할 수 있다.")
    void find_all_user() {
        //given
        User user = new User("rosie@google.com", "password");
        User otherUser = new User("poz@wooteco.com", "1234");
        List<User> users = List.of(user, otherUser);
        given(userRepository.findAll()).willReturn(users);
        //when
        List<User> result = userService.findAllUser();
        //then
        Assertions.assertThat(result).contains(user, otherUser);
    }

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
            assertDoesNotThrow(() -> userService.authorizeUser(email, password));
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않는 경우 예외를 반환한다.")
        void authorize_user_fail() {
            //given
            String email = "a@a.com";
            String password = "password";
            given(userRepository.findByEmail(email)).willReturn(Optional.of(new User(email, "original")));

            //when
            //then
            assertThatThrownBy(() -> userService.authorizeUser(email, password))
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
            //then
            assertThatThrownBy(() -> userService.authorizeUser(email, password))
                    .isInstanceOf(UserNotFoundException.class);
        }

    }
}