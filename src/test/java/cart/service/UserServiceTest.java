package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.entity.User;
import cart.dao.user.UserDao;
import cart.dto.user.UserResponse;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserDao userDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    @DisplayName("등록된 모든 회원의 정보를 반환한다.")
    void findAll() {
        userDao.saveUser(new User("ahdjd5@gmail.com", "qwer1234"));
        userDao.saveUser(new User("ahdjd5@naver.com", "qwer1234"));

        final List<UserResponse> users = userService.findAll();

        assertAll(
                () -> assertThat(users).hasSize(2),
                () -> assertThat(users.get(0).getEmail()).isEqualTo("ahdjd5@gmail.com"),
                () -> assertThat(users.get(0).getPassword()).isEqualTo("qwer1234")
        );
    }
}
