package cart.service;

import cart.dao.UserDao;
import cart.dto.AuthRequest;
import cart.exception.AuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthService authService;

    @DisplayName("Basic 문자열이 들왔을 때 해당 유저의 정보가 있는지 판단하고 id를 반환한다.")
    @Test
    void findUserByAuthSuccessTest() {
        when(userDao.selectByAuth(any())).thenReturn(1);

        Integer userId = authService.findUserIdByAuth(new AuthRequest("email@email.com", "password"));

        assertThat(userId).isEqualTo(1);
    }

    @DisplayName("Basic 문자열이 들어왔을 때 해당 유저의 정보가 없다면 예외를 반환한다.")
    @Test
    void findUserByAuthFailTest() {
        when(userDao.selectByAuth(any())).thenReturn(null);

        assertThatThrownBy(() -> authService.findUserIdByAuth(new AuthRequest("email@email.com", "password")))
                .isInstanceOf(AuthorizationException.class);
    }
}
