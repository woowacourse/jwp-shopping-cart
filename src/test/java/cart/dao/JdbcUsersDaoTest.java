package cart.dao;

import cart.entity.User;
import cart.entity.vo.Email;
import cart.exception.UserAuthorizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class JdbcUsersDaoTest {

    private final JdbcUsersDao usersDao;

    @Autowired
    public JdbcUsersDaoTest(final JdbcTemplate jdbcTemplate) {
        this.usersDao = new JdbcUsersDao(jdbcTemplate);
    }

    @Test
    @DisplayName("등록된 사용자들 검색 테스트")
    void find_all_user_test() {
        // when
        final List<User> users = usersDao.findAll();

        // then
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("이메일로 사용자 검색 테스트")
    void find_by_email_test() {
        // given
        final Email email = new Email("test@email.com");
        final String expectedPassword = "12345";

        // when
        final User user = usersDao.findByEmail(email);

        // then
        assertThat(user.getPassword()).isEqualTo(expectedPassword);
    }

    @Test
    @DisplayName("등록되지 않은 사용자로 조회시 예외 발생")
    void unregistered_user_find_exception_test() {
        // given
        final Email unregisteredEmail = new Email("unRegistered@eamil.com");

        // when & then
        assertThatThrownBy(() -> usersDao.findByEmail(unregisteredEmail))
                .isInstanceOf(UserAuthorizationException.class)
                .hasMessage("입력된 email을 사용하는 사용자를 찾을 수 없습니다. 입력된 email : " + unregisteredEmail);
    }
}
