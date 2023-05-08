package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import cart.entiy.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class UserDaoTest {

    private UserDao userDao;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        userDao = new UserDao(jdbcTemplate);
    }

    @Test
    void 생성_테스트() {
        final UserEntity userEntity = userDao.insert(new UserEntity("a@a.com", "password1"));
        final Optional<UserEntity> result = userDao.findByEmail("a@a.com");
        assertAll(
                () -> assertThat(userEntity.getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(userEntity.getPassword()).isEqualTo("password1"),
                () -> assertThat(result).isPresent(),
                () -> assertThat(result.get().getEmail()).isEqualTo("a@a.com"),
                () -> assertThat(result.get().getPassword()).isEqualTo("password1")
        );
    }

    @Test
    void 이메일_조회_테스트() {
        final Optional<UserEntity> userEntity = userDao.findByEmail("a@a.com");
        assertThat(userEntity).isEmpty();
    }

    @Test
    void 조회_테스트() {
        final List<UserEntity> userEntities = userDao.findAll();
        assertThat(userEntities).hasSize(0);
    }
}
