package cart.service;

import cart.dao.JdbcUserDao;
import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@Sql(scripts = {"classpath:test.sql"})
public class UserServiceTest {

    private final UserService userService;

    public UserServiceTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.userService = new UserService(new JdbcUserDao(jdbcTemplate));
    }

    @Test
    @DisplayName("사용자를 저장한다")
    void save() {
        assertDoesNotThrow(() -> userService.save("test12@mail.com", "12121212"));
    }

    @Test
    @DisplayName("Email로 사용자 리스트를 조회한다")
    void findByEmail() {
        final String email = "test12@mail.com";
        final long id = userService.save(email, "12121212");

        final User actual = userService.findByEmail(email);

        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("사용자 리스트를 조회한다")
    void findAll() {
        userService.save("test12@mail.com", "12121212");
        userService.save("test34@mail.com", "34343434");

        final List<User> actual = userService.findAll();

        assertThat(actual).extracting("email")
                .containsExactly("test12@mail.com", "test34@mail.com");
    }

    @Test
    @DisplayName("Id로 사용자 존재여부를 검증한다")
    void validateExistsUserId() {
        final long id = userService.save("test12@mail.com", "12121212");

        assertAll(
                () -> assertDoesNotThrow(() -> userService.validateExistUserId(id)),
                () -> assertThatThrownBy(() -> userService.validateExistUserId(-1L)).isInstanceOf(IllegalArgumentException.class)
        );
    }
}
