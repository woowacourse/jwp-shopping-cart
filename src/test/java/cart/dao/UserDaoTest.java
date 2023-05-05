package cart.dao;

import static cart.TestFixture.EMAIL_0CHIL;
import static cart.TestFixture.EMAIL_BEAVER;
import static cart.TestFixture.PASSWORD_0CHIL;
import static cart.TestFixture.PASSWORD_BEAVER;
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
        userDao.insert(EMAIL_0CHIL, PASSWORD_0CHIL);
        userDao.insert(EMAIL_BEAVER, PASSWORD_BEAVER);
    }

    @DisplayName("사용자를 삽입한다")
    @Test
    void insertUser() {
        assertThat(userDao.selectAll())
                .extracting("email", "password")
                .contains(
                        tuple(EMAIL_0CHIL, PASSWORD_0CHIL),
                        tuple(EMAIL_BEAVER, PASSWORD_BEAVER)
                );
    }

    @DisplayName("사용자를 전체 조회한다")
    @Test
    void selectAll() {
        assertThat(userDao.selectAll())
                .extracting("email", "password")
                .containsExactlyInAnyOrder(
                        tuple(EMAIL_0CHIL, PASSWORD_0CHIL),
                        tuple(EMAIL_BEAVER, PASSWORD_BEAVER)
                );
    }

    @DisplayName("사용자를 Id로 조회한다")
    @Test
    void selectById() {
        assertThat(userDao.selectBy(getGreatestId()))
                .extracting("email", "password")
                .containsExactly(
                        EMAIL_BEAVER, PASSWORD_BEAVER
                );
    }

    @DisplayName("사용자를 이메일로 조회한다")
    @Test
    void selectByEmail() {
        assertThat(userDao.selectBy(EMAIL_0CHIL))
                .extracting("email", "password")
                .containsExactly(
                        EMAIL_0CHIL, PASSWORD_0CHIL
                );
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM users", Integer.class);
    }
}
