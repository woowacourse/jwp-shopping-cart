package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cart.dao.entity.User;

@JdbcTest
class JdbcUserDaoTest {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new JdbcUserDao(namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("등록된 모든 회원의 이메일과 패스워드를 조회한다.")
    void findAll() {
        final List<User> users = userDao.findAll();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("ahdjd5@gmail.com");
    }
}
