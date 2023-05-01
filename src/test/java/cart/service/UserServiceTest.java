package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cart.dto.user.UserResponse;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("등록된 모든 회원의 정보를 반환한다.")
    void findAll() {
        final List<UserResponse> users = userService.findAll();
        assertAll(
                () -> assertThat(users).hasSize(1),
                () -> assertThat(users.get(0).getEmail()).isEqualTo("ahdjd5@gmail.com"),
                () -> assertThat(users.get(0).getPassword()).isEqualTo("qwer1234")
        );
    }
}
