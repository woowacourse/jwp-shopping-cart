package cart.dao;

import cart.dao.entity.UserEntity;
import cart.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class UserDaoTest {

    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
    }

    @Test
    void 모든_데이터를_조회한다() {
        //given
        userDao.insert(new User("huchu@woowahan.com", "1234567a!"));

        //when
        final List<UserEntity> userEntities = userDao.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(userEntities).hasSize(1);
            final UserEntity userEntity = userEntities.get(0);
            softly.assertThat(userEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(userEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void 데이터를_추가한다() {
        //given
        final User user = new User("huchu@woowahan.com", "1234567a!");

        //when
        final Long id = userDao.insert(user);

        //then
        assertSoftly(softly -> {
            softly.assertThat(id).isNotNull();
            final UserEntity userEntity = userDao.findById(id);
            softly.assertThat(userEntity.getId()).isEqualTo(id);
            softly.assertThat(userEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(userEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void id로_데이터를_찾는다() {
        //given
        final User user = new User("huchu@woowahan.com", "1234567a!");
        final Long id = userDao.insert(user);

        //when
        final UserEntity userEntity = userDao.findById(id);

        //then
        assertSoftly(softly -> {
            softly.assertThat(userEntity.getId()).isEqualTo(id);
            softly.assertThat(userEntity.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(userEntity.getPassword()).isEqualTo("1234567a!");
        });
    }

    @Test
    void 모든_데이터를_삭제한다() {
        //given
        userDao.insert(new User("huchu@woowahan.com", "1234567a!"));

        //when
        userDao.deleteAll();

        //then
        assertThat(userDao.findAll()).hasSize(0);
    }
}
