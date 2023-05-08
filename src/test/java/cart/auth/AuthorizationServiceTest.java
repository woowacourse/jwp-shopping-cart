package cart.auth;

import cart.controller.auth.AuthorizationService;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private AuthorizationService authorizationService;

    @DisplayName("이메일과 비밀번호가 일치하면 true를 반환한다.")
    @Test
    void checkLogin() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        User user = new User(new Email(email), new Password(password));
        Mockito.when(userDao.findBy(Mockito.anyString())).thenReturn(Optional.of(user));
        //when
        boolean check = authorizationService.checkLogin(email, password);
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
        boolean check = authorizationService.checkLogin(email, password);
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
        boolean check = authorizationService.checkLogin(email, "123456781");
        //then
        assertThat(check).isFalse();
    }
}
