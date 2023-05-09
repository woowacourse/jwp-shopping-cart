package cart.dao;

import cart.domain.AuthInfo;
import cart.dto.AuthRequest;
import cart.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
class JdbcTemplateUserDaoTest {
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        userDao = new JdbcTemplateUserDao(jdbcTemplate);
    }

    @DisplayName("insert 테스트")
    @Test
    void insertTest() {
        int id = userDao.insert(new UserEntity(null, "email", "password"));

        UserEntity user = findById(id);

        assertSoftly(softly -> {
            softly.assertThat(user.getId()).isEqualTo(id);
            softly.assertThat(user.getEmail()).isEqualTo("email");
            softly.assertThat(user.getPassword()).isEqualTo("password");
        });
    }

    @DisplayName("selectByAuth 테스트")
    @Test
    void selectByAuthTest() {
        int userId = userDao.insert(new UserEntity(null, "email@email.com", "password"));
        int selectedUserId = userDao.selectByAuth(new AuthInfo("email@email.com", "password"));

        Assertions.assertThat(selectedUserId).isEqualTo(userId);
    }

    @DisplayName("selectAll 테스트")
    @Test
    void selectAllTest() {
        userDao.insert(new UserEntity(null, "email1", "password1"));
        userDao.insert(new UserEntity(null, "email2", "password2"));

        List<UserEntity> users = userDao.selectAll();
        Assertions.assertThat(users).hasSize(2);
    }

    @DisplayName("update 테스트")
    @Test
    void updateTest() {
        int userId = userDao.insert(new UserEntity(null, "email1@email.com", "password1"));
        userDao.update(new UserEntity(userId, "email2@email.com", "password2"));
        Integer selectedUserId = userDao.selectByAuth(new AuthInfo("email2@email.com", "password2"));

        Assertions.assertThat(selectedUserId).isEqualTo(userId);
    }

    @DisplayName("delete 테스트")
    @Test
    void deleteTest() {
        int userId = userDao.insert(new UserEntity(null, "email1@email.com", "password1"));
        userDao.delete(userId);
        Assertions.assertThatThrownBy(() -> userDao.selectByAuth(new AuthInfo("email1@email.com", "password1")))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    private UserEntity findById(final int id) {
        String sql = "select * from users where id = ?";

        RowMapper<UserEntity> userEntityRowMapper = (resultSet, rowNumber) -> new UserEntity(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );

        return jdbcTemplate.queryForObject(sql, userEntityRowMapper, id);
    }
}
