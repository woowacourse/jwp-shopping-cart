package cart.dao;

import cart.JdbcSaveUser;
import cart.dao.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
class JdbcUserDaoTest extends JdbcSaveUser {

    private UserDao userDao;

    @BeforeEach
    void init() {
        userDao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    void id로_조회한다() {
        // given
        final long saveId = 사용자를_저장한다("test@test.com", "test");

        // when
        final Users user = userDao.findById(saveId).get();

        // then
        사용자_정보를_비교한다(user, saveId, "test@test.com", "test");
    }

    @Test
    void 전체_조회한다() {
        // given
        final long firstUser = 사용자를_저장한다("test@test.com", "test");
        final long secondUser = 사용자를_저장한다("user@user.com", "user");

        // when
        final List<Users> users = userDao.findAll();

        // then
        assertAll(
                () -> assertThat(users).hasSize(2),
                () -> 사용자_정보를_비교한다(users.get(0), firstUser, "test@test.com", "test"),
                () -> 사용자_정보를_비교한다(users.get(1), secondUser, "user@user.com", "user")
        );
    }


    private void 사용자_정보를_비교한다(final Users target, final long id, final String email, final String password) {
        assertAll(
                () -> assertThat(target.getId()).isEqualTo(id),
                () -> assertThat(target.getEmail()).isEqualTo(email),
                () -> assertThat(target.getPassword()).isEqualTo(password)
        );
    }
}
