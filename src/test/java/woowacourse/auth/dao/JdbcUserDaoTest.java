package woowacourse.auth.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.auth.Fixtures.USER_ENTITY_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.entity.UserEntity;

@Sql(scripts = {"classpath:schema.sql"})
@JdbcTest
class JdbcUserDaoTest {
    private final UserDao userDao;

    @Autowired
    public JdbcUserDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        userDao = new JdbcUserDao(jdbcTemplate, dataSource);
    }

    @DisplayName("User 도메인 객체를 전달받아 데이터베이스에 추가한다.")
    @Test
    void save() {
        // when
        int actual = userDao.save(USER_ENTITY_1);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("User id를 전달받아 해당하는 User 객체를 조회한다.")
    @Test
    void findById() {
        //given
        int userId = userDao.save(USER_ENTITY_1);

        // when
        UserEntity actual = userDao.findById(userId);

        // then
        assertThat(actual).extracting("id", "email", "password", "profileImageUrl", "terms")
                .containsExactly(userId, USER_ENTITY_1.getEmail(), USER_ENTITY_1.getPassword(),
                        USER_ENTITY_1.getProfileImageUrl(), USER_ENTITY_1.isTerms());
    }
}
