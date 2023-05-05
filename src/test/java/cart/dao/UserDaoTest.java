package cart.dao;

import cart.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@JdbcTest
class UserDaoTest {

    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
    }

    @DisplayName("모든 유저를 불러올 수 있다")
    @Test
    void findAll() {
        //given
        final int count = countRowsInTable(jdbcTemplate, "users");

        //then
        assertThat(userDao.findAll()).hasSize(count);
    }

    @DisplayName("유저를 이메일로 찾을 수 있다")
    @Test
    void findByEmail() {
        //given
        final User user = userDao.findAll().get(0);

        //then
        assertAll(
                () -> assertThat(userDao.findByEmail(user.getEmail())).isPresent(),
                () -> assertThat(userDao.findByEmail(" ")).isNotPresent());
    }
}
