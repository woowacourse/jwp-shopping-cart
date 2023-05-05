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
import java.util.Optional;

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

    @DisplayName("이메일과 비밀번호가 일치하면 true를 반환한다.")
    @Test
    void checkLogin() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        User user = new User(new Email(email), new Password(password));
        Mockito.when(userDao.findBy(Mockito.anyString())).thenReturn(Optional.of(user));
        //when
        boolean check = userService.checkLogin(email, password);
        //then
        assertThat(check).isTrue();
    }

    @DisplayName("이메일이 없으면 false를 반환한다.")
    @Test
    void checkLoginReturnFalseWithNoExistEmail() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        Mockito.when(userDao.findBy(email)).thenReturn(Optional.empty());
        //when
        boolean check = userService.checkLogin(email, password);
        //then
        assertThat(check).isFalse();
    }

    @DisplayName("비밀번호가 일치하지 않으면 false를 반환한다.")
    @Test
    void checkLoginReturnFalseWithNotMatchesPassword() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        User user = new User(new Email(email), new Password(password));
        Mockito.when(userDao.findBy(Mockito.anyString())).thenReturn(Optional.of(user));
        //when
        boolean check = userService.checkLogin(email, "123456781");
        //then
        assertThat(check).isFalse();
    }
}
