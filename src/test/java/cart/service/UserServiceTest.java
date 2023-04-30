package cart.service;

import cart.controller.dto.UserDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.UserDao;
import cart.persistence.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserService.class)
class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자 정보를 저장한다.")
    void save() {
        // given
        final UserDto userDto = new UserDto(1L, "journey@gmail.com", "password", "져니", "010-1234-5678");
        final UserEntity userEntity = new UserEntity("journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(userDao.insert(any())).thenReturn(1L);
        when(userDao.findById(1L)).thenReturn(Optional.of(userEntity));

        // when
        final long savedUserId = userService.save(userDto);

        // then
        final UserDto result = userService.getById(savedUserId);
        assertThat(result)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효한 사용자 아이디가 주어지면, 사용자 정보를 반환한다.")
    void getById_success() {
        // given
        final UserEntity userEntity = new UserEntity("journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(userDao.findById(any())).thenReturn(Optional.of(userEntity));

        // when
        final UserDto userDto = userService.getById(1L);

        // then
        assertThat(userDto)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly("journey@gmail.com", "password", "져니", "010-1234-5678");
    }

    @Test
    @DisplayName("유효하지 않은 사용자 아이디가 주어지면, 예외가 발생한다.")
    void getById_fail() {
        assertThatThrownBy(() -> userService.getById(1L))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.USER_NOT_FOUND);
    }
}
