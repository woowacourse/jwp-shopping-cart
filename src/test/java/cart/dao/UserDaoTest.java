package cart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        assertDoesNotThrow(() -> userDao.findAll());
    }
}
