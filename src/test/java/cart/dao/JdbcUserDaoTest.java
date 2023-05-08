package cart.dao;

import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql(scripts = {"classpath:test.sql"})
class JdbcUserDaoTest {

    private final JdbcUserDao jdbcUserDao;

    private JdbcUserDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcUserDao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    @DisplayName("User 삽입 테스트")
    void insert() {
        final long id = jdbcUserDao.insert(new User("IO@mail.com", "testpassword"));
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("User ID로 조회 테스트")
    void findById() {
        final String email1 = "IO@mail.com";
        final long id1 = jdbcUserDao.insert(new User(email1, "testpassword"));
        final String email2 = "ASH@mail.com";
        final long id2 = jdbcUserDao.insert(new User(email2, "testpassword"));

        final Optional<User> user1 = jdbcUserDao.findById(id1);
        final Optional<User> user2 = jdbcUserDao.findById(id2);

        assertAll(
                () -> assertThat(user1).isPresent(),
                () -> assertThat(user1.get().getEmail()).isEqualTo(email1),
                () -> assertThat(user2).isPresent(),
                () -> assertThat(user2.get().getEmail()).isEqualTo(email2)
        );
    }

    @Test
    @DisplayName("User Email로 조회 테스트")
    void finByEmail() {
        final long id1 = jdbcUserDao.insert(new User("IO@mail.com", "testpassword"));
        final long id2 = jdbcUserDao.insert(new User("ASH@mail.com", "testpassword"));

        final Optional<User> user1 = jdbcUserDao.findByEmail("IO@mail.com");
        final Optional<User> user2 = jdbcUserDao.findByEmail("ASH@mail.com");

        assertAll(
                () -> assertThat(user1).isPresent(),
                () -> assertThat(user1.get().getId()).isEqualTo(id1),
                () -> assertThat(user2).isPresent(),
                () -> assertThat(user2.get().getId()).isEqualTo(id2)
        );
    }

    @Test
    @DisplayName("User 조회 테스트")
    void findAll() {
        jdbcUserDao.insert(new User("IO@mail.com", "testpassword"));
        jdbcUserDao.insert(new User("ASH@mail.com", "testpassword"));
        jdbcUserDao.insert(new User("BROWN@mail.com", "testpassword"));

        assertThat(jdbcUserDao.findAll()).extracting("email")
                .containsExactly("IO@mail.com", "ASH@mail.com", "BROWN@mail.com");
    }
}
