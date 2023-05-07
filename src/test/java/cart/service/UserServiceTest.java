package cart.service;

import cart.controller.dto.request.UserRequest;
import cart.controller.dto.response.UserResponse;
import cart.dao.UserDao;
import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import cart.exception.NotFoundResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @DisplayName("사용자를 저장한다")
    @Test
    void saveUser() {
        //given
        User user = new User.Builder()
                .email(new Email("test@email.com"))
                .password(new Password("testPassword"))
                .build();
        when(userDao.save(user)).thenReturn(1L);
        //when
        Long id = userService.saveUser(new UserRequest("test@email.com", "testPassword"));
        //then
        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("모든 사용자를 불러온다")
    @Test
    void loadAllUser() {
        //given
        User user1 = new User.Builder().id(1L)
                                       .email(new Email("test1@email.com"))
                                       .password(new Password("test1Password"))
                                       .build();
        User user2 = new User.Builder().id(2L)
                                       .email(new Email("test2@email.com"))
                                       .password(new Password("test2Password"))
                                       .build();
        User user3 = new User.Builder().id(3L)
                                       .email(new Email("test3@email.com"))
                                       .password(new Password("test3Password"))
                                       .build();
        when(userDao.findAll()).thenReturn(List.of(user1, user2, user3));
        //when
        List<UserResponse> userResponses = userService.loadAllUser();
        //then
        assertThat(userResponses).contains(UserResponse.from(user1), UserResponse.from(user2), UserResponse.from(user3));
    }

    @DisplayName("특정 아이디의 사용자를 불러온다")
    @Test
    void loadUser() {
        //given
        User user = new User.Builder().id(1L)
                                      .email(new Email("test1@email.com"))
                                      .password(new Password("test1Password"))
                                      .build();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        //when
        UserResponse userResponse = userService.loadUser(1L);
        //then
        assertThat(userResponse).isEqualTo(UserResponse.from(user));
    }

    @DisplayName("존재하지 않는 아이디의 사용자를 불러오면 예외가 발생한다")
    @Test
    void loadUserExceptionWithNotExist() {
        //given
        when(userDao.findById(100L)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() ->
                userService.loadUser(100L)
        ).isInstanceOf(NotFoundResultException.class);
    }

    @DisplayName("사용자를 수정한다")
    @Test
    void updateUser() {
        //given
        User user = new User.Builder().id(1L)
                                      .email(new Email("edit@email.com"))
                                      .password(new Password("editPassword"))
                                      .build();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userDao)
                   .update(user);
        //when
        userService.updateUser(1L, new UserRequest("edit@email.com", "editPassword"));
        //then
        verify(userDao, times(1)).update(user);
    }

    @DisplayName("존재하지 않는 아이디의 사용자를 수정하면 예외가 발생한다")
    @Test
    void updateUserExceptionWithNotExist() {
        //given
        when(userDao.findById(100L)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() ->
                userService.updateUser(100L,
                        new UserRequest("edit@email.com", "editPassword"))
        ).isInstanceOf(NotFoundResultException.class);
    }

    @DisplayName("사용자를 삭제한다")
    @Test
    void deleteUser() {
        //given
        User user = new User.Builder().id(1L)
                                      .email(new Email("test@email.com"))
                                      .password(new Password("testPassword"))
                                      .build();
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userDao)
                   .deleteBy(1L);
        //when
        userService.deleteUser(1L);
        //then
        verify(userDao, times(1)).deleteBy(1L);
    }

    @DisplayName("존재하지 않는 아이디의 사용자를 삭제하면 예외가 발생한다")
    @Test
    void deleteUserExceptionWithNotExist() {
        //given
        when(userDao.findById(100L)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() ->
                userService.deleteUser(100L)
        ).isInstanceOf(NotFoundResultException.class);
    }
}
