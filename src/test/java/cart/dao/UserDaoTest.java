package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class UserDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
    }

    @Test
    @DisplayName("지정한 email의 사용자를 조회한다.")
    void findByIdSuccess() {
        final Optional<User> user = userDao.findByEmail("a@a.com");

        assertAll(
                () -> assertThat(user).isPresent(),
                () -> assertThat(user.get().getEmail()).isEqualTo("a@a.com")
        );
    }

    @Test
    @DisplayName("모든 사용자를 조회한다.")
    void findAllSuccess() {
        final List<User> users = userDao.findAll();

        assertThat(users).hasSize(2);
    }
}
