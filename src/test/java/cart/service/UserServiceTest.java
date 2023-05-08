package cart.service;

import cart.dao.UserDao;
import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final User testUser = new User(1L, "IO@mail.com", "12121212");

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDao;

    @Test
    @DisplayName("사용자를 저장한다")
    void save() {
        given(userDao.insert(any())).willReturn(1L);

        assertThat(userService.save("test12@mail.com", "12121212")).isEqualTo(1L);
        verify(userDao, times(1)).insert(any());
    }

    @Test
    @DisplayName("Email로 사용자 리스트를 조회한다")
    void findByEmail() {
        given(userDao.findByEmail(anyString())).willReturn(Optional.of(testUser));

        final User actual = userService.findByEmail(testUser.getEmail());

        assertThat(actual.getId()).isEqualTo(1L);
        verify(userDao, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("사용자 리스트를 조회한다")
    void findAll() {
        given(userDao.findAll()).willReturn(List.of(testUser));

        final List<User> actual = userService.findAll();

        assertAll(
                () -> assertThat(actual.size()).isEqualTo(1),
                () -> assertThat(actual.get(0).getEmail()).isEqualTo(testUser.getEmail())
        );
        verify(userDao, times(1)).findAll();
    }
}
