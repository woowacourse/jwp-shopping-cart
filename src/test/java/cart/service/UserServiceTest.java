package cart.service;

import static org.mockito.BDDMockito.given;

import cart.domain.User;
import cart.repository.UserRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
}