package cart.service;

import cart.controller.dto.UserResponse;
import cart.dao.UserDao;
import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;

    @DisplayName("모든 유저 목록을 불러온다.")
    @Test
    void loadAllUser() {
        //given
        User user1 = new User(new Email("email1@email.com"), new Password("12345678"));
        User user2 = new User(new Email("email2@email.com"), new Password("12345678"));
        User user3 = new User(new Email("email3@email.com"), new Password("12345678"));
        Mockito.when(userDao.findAll()).thenReturn(List.of(user1, user2, user3));
        //when
        List<UserResponse> userResponses = userService.loadAllUser();
        //then
        assertThat(userResponses).hasSize(3);
    }
}
