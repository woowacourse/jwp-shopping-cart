package cart.service;

import cart.JdbcSaveUser;
import cart.dao.JdbcUserDao;
import cart.dao.UserResponses;
import cart.dao.entity.Users;
import cart.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
class UserServiceTest extends JdbcSaveUser {

    private UserService userService;

    private JdbcUserDao userDao;

    @BeforeEach
    void init() {
        userDao = new JdbcUserDao(jdbcTemplate);
        userService = new UserService(userDao);
    }

    @Test
    void 단건_조회를_한다() {
        // given
        final long savedId = 사용자를_저장한다("test@test.com", "test");

        // when
        final UserResponse result = findById(savedId);

        // then
        assertAll(
                () -> assertThat(result.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(result.getPassword()).isEqualTo("test")
        );
    }

    private UserResponse findById(final Long id) {
        final String sql = "SELECT id, email, password, created_at FROM users WHERE id = :id";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final Users users = jdbcTemplate.queryForObject(
                sql,
                Collections.singletonMap("id", id),
                createUsersRowMapper()
        );

        return new UserResponse(users);
    }

    private static RowMapper<Users> createUsersRowMapper() {
        return (rs, rowNum) -> new Users(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Test
    void 전체_조회를_한다() {
        // given
        final long firstId = 사용자를_저장한다("test@test.com", "test");
        final long secondId = 사용자를_저장한다("user@user.com", "user");

        // when
        final UserResponses userResponses = userService.findAll();

        // then
        final List<UserResponse> results = userResponses.getUserResponses();
        assertAll(
                () -> assertThat(results).hasSize(2),
                () -> assertThat(getEmail(0, results)).isEqualTo("test@test.com"),
                () -> assertThat(getPassword(0, results)).isEqualTo("test"),
                () -> assertThat(getEmail(1, results)).isEqualTo("user@user.com"),
                () -> assertThat(getPassword(1, results)).isEqualTo("user")
        );
    }

    private static String getEmail(final int index, final List<UserResponse> results) {
        return results.get(index).getEmail();
    }

    private static String getPassword(final int index, final List<UserResponse> results) {
        return results.get(index).getPassword();
    }
}
