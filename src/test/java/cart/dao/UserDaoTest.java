package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class UserDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        this.userDao = new UserDao(jdbcTemplate);
        userDao.insert("0@chll.it", "verysecurepassword");
        userDao.insert("beaver@wooteco.com", "veryverysecurepassword");
    }

    @DisplayName("사용자를 삽입한다")
    @Test
    void insertUser() {
        assertThat(userDao.selectAll())
                .extracting("email", "password")
                .contains(
                        tuple("0@chll.it", "verysecurepassword"),
                        tuple("beaver@wooteco.com", "veryverysecurepassword")
                );
    }

    @DisplayName("사용자를 전체 조회한다")
    @Test
    void selectAll() {
        assertThat(userDao.selectAll())
                .extracting("email", "password")
                .containsExactlyInAnyOrder(
                        tuple("0@chll.it", "verysecurepassword"),
                        tuple("beaver@wooteco.com", "veryverysecurepassword")
                );
    }
}
