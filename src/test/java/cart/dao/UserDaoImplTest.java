package cart.dao;

import cart.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(UserDaoImpl.class)
class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDao;

    @DisplayName("기존에 존재하는 사용자의 정보를 이메일을 통해 가져올 수 있다.")
    @Test
    void findUserByEmail() {
        // given
        final Optional<User> userOptional = userDao.findUserByEmail("a@a.com");

        // when
        final User user = userOptional.get();

        // then
        assertThat(user.getUserEmailValue()).isEqualTo("a@a.com");
    }

    @DisplayName("모든 사용자의 정보를 가져올 수 있다.")
    @Test
    void findAll() {
        assertDoesNotThrow(() -> userDao.findAll());
    }
}