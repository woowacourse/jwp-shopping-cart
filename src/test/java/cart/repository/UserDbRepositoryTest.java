package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cart.domain.User;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@JdbcTest
class UserDbRepositoryTest {
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        userRepository = new UserDbRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("데이터베이스의 모든 유저를 조회할 수 있다.")
    void find_all_success() {
        //given
        Long actualCount = jdbcTemplate.queryForObject("SELECT count(*) as count FROM users",
                (rs, __) -> rs.getLong("count"));
        //when
        List<User> all = userRepository.findAll();
        //then
        assertThat(all).hasSize(actualCount.intValue());
    }

    @Test
    @DisplayName("이메일로 유저를 읽어올 수 있다.")
    void find_by_email_success() {
        //given
        String email = "a@a.com";
        String password = "password";

        insertUser(email, password);

        //when
        User foundUser = userRepository.findByEmail(email).get();

        //then
        assertAll(
                () -> assertThat(foundUser).isNotNull(),
                () -> assertThat(foundUser.getEmail()).isEqualTo(email),
                () -> assertThat(foundUser.getPassword()).isEqualTo(password)
        );
    }

    private void insertUser(String email, String password) {
        User user = new User(email, password);

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(user));
    }

}