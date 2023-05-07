package cart.dao;

import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Sql("classpath:initializeTestDb.sql")
class UserDaoTest {

    private final RowMapper<User> actorRowMapper = (resultSet, rowNumber) -> new User.Builder()
            .id(resultSet.getLong("id"))
            .email(new Email(resultSet.getString("email")))
            .password(new Password(resultSet.getString("password")))
            .build();
    private UserDao userDao;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    UserDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.userDao = new UserDao(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @DisplayName("아이템을 저장한다.")
    @Test
    void save() {
        // given
        User user = new User.Builder()
                .email(new Email("test@email.com"))
                .password(new Password("testPassword"))
                .build();
        // when
        Long userId = userDao.save(user);
        // then
        User findUser = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = :id",
                new MapSqlParameterSource("id", userId),
                actorRowMapper
        );
        assertThat(findUser).isEqualTo(new User.Builder()
                .id(userId)
                .email(new Email("test@email.com"))
                .password(new Password("testPassword"))
                .build()
        );
    }

    @DisplayName("사용자의 전체 목록을 조회한다")
    @Test
    void findAll() {
        //when
        List<User> users = userDao.findAll();
        //then
        assertThat(users).hasSize(3);
    }

    @DisplayName("아이디를 통해 사용자를 조회한다")
    @Test
    void findById() {
        //when
        User findUser = userDao.findById(1L)
                               .orElseThrow();
        //then
        assertThat(findUser).isEqualTo(new User.Builder()
                .id(1L)
                .email(new Email("test1@gmail.com"))
                .password(new Password("test1pw1234"))
                .build()
        );
    }

    @DisplayName("아이디를 통해 사용자를 조회한다")
    @Test
    void findByNotExistId() {
        //when
        Optional<User> findUser = userDao.findById(100L);
        //then
        assertThat(findUser).isEmpty();
    }

    @DisplayName("이메일을 통해 사용자를 조회한다")
    @Test
    void findByEmail() {
        //when
        User findUser = userDao.findByEmail("test1@gmail.com")
                               .orElseThrow();
        //then
        assertThat(findUser).isEqualTo(new User.Builder()
                .id(1L)
                .email(new Email("test1@gmail.com"))
                .password(new Password("test1pw1234"))
                .build()
        );
    }

    @DisplayName("없는 아이디를 통해 사용자를 조회한다")
    @Test
    void findByNotExistEmail() {
        //when
        Optional<User> findUser = userDao.findByEmail("NotExist@email.com");
        //then
        assertThat(findUser).isEmpty();
    }

    @DisplayName("사용자를 수정한다")
    @Test
    void update() {
        //given
        User editUser = new User.Builder()
                .id(1L)
                .email(new Email("editTest1@gmail.com"))
                .password(new Password("editTest1pw1234"))
                .build();
        //when
        userDao.update(editUser);
        //then
        User findUser = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = :id",
                new MapSqlParameterSource("id", editUser.getId()),
                actorRowMapper
        );
        assertThat(findUser).isEqualTo(editUser);
    }

    @DisplayName("사용자를 삭제한다")
    @Test
    void delete() {
        //given
        User targetUser = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = :id",
                new MapSqlParameterSource("id", 1L),
                actorRowMapper
        );
        //when
        userDao.deleteBy(targetUser.getId());
        //then
        List<User> findItems = namedParameterJdbcTemplate.query(
                "SELECT * FROM users",
                actorRowMapper
        );
        assertThat(findItems).doesNotContain(targetUser);
    }
}
