package woowacourse.auth.acceptance.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.TestConstructor;
import woowacourse.auth.dao.UserDao2;
import woowacourse.auth.domain.User2;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(Lifecycle.PER_CLASS)
class UserDao2Test {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final UserDao2 userDao;

    public UserDao2Test(NamedParameterJdbcTemplate jdbcTemplate) {
        userDao = new UserDao2(jdbcTemplate);
    }

    @DisplayName("findByUserName 메서드는 아이디(username)에 해당되는 사용자 데이터를 Optional로 반환")
    @Nested
    class FindByUserNameTest {

        @Test
        void 존재하는_사용자인_경우_값이_있는_Optional_반환() {
            String 유효한_아이디 = "valid_username";
            String 비밀번호 = "valid_pw";
            User2 사용자 = new User2(유효한_아이디, 비밀번호);
            사용자_저장(사용자);

            User2 actual = userDao.findByUserName(유효한_아이디).get();

            assertThat(actual).isEqualTo(사용자);
        }

        @Test
        void 존재하지_않는_사용자인_경우_값이_없는_Optional_반환() {
            String 존재하지_않는_아이디 = "non_existing";

            boolean exists = userDao.findByUserName(존재하지_않는_아이디).isPresent();

            assertThat(exists).isFalse();
        }
    }

    private void 사용자_저장(User2 user) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, '닉네임', 15)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);

        jdbcTemplate.update(sql, params);
    }
}
