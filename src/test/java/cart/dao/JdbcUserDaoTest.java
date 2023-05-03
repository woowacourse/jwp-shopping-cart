package cart.dao;

import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:data.sql"})
class JdbcUserDaoTest {

    private final JdbcUserDao jdbcUserDao;

    private JdbcUserDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcUserDao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Product 삽입 테스트")
    void insertTest() {
        final Long id = jdbcUserDao.insert(new User("IO@mail.com", "testpassword"));
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("User 조회 테스트")
    void findAllTest() {
        jdbcUserDao.insert(new User("IO@mail.com", "testpassword"));
        jdbcUserDao.insert(new User("ASH@mail.com", "testpassword"));
        jdbcUserDao.insert(new User("BROWN@mail.com", "testpassword"));

        assertThat(jdbcUserDao.findAll()).extracting("email")
                .containsExactly("IO@mail.com", "ASH@mail.com", "BROWN@mail.com");
    }
}
