package cart.dao;

import cart.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class UserDaoTest {

    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoTest(final JdbcTemplate jdbcTemplate) {
        this.userDao = new UserDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("모든 유저 정보를 가져온다.")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void findAll() {
        //when
        List<User> allUser = userDao.findAll();
        //then
        assertThat(allUser).hasSize(2);
    }

    @DisplayName("이메일로 유저 정보를 가져온다")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void findByEmail() {
        //given
        final String email = "email1@email.com";
        //when
        Optional<User> user = userDao.findBy(email);
        //then
        assertThat(user.get().getEmail()).isEqualTo(email);
    }

    @DisplayName("아이로 유저 정보를 가져온다")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void findById() {
        //given
        final Long id = 1L;
        //when
        Optional<User> user = userDao.findBy(id);
        //then
        assertThat(user.get().getId()).isEqualTo(id);
    }
}
