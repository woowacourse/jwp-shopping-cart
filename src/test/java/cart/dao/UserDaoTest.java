package cart.dao;

import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class UserDaoTest {

    private final UserDao userDao;

    @Autowired
    public UserDaoTest(final JdbcTemplate jdbcTemplate) {
        this.userDao = new UserDao(jdbcTemplate);
    }

    @DisplayName("유저를 전부 조회한다.")
    @Test
    void findAllTest() {
        final List<User> allUsers = userDao.findAll();

        assertThat(allUsers).hasSize(2);
    }
}
