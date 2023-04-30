package cart.dao;

import cart.dao.entity.Users;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Users> findById(final Long id) {
        final String sql = "SELECT id, email, password, created_at FROM users WHERE id = :id";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final Users users = jdbcTemplate.queryForObject(
                sql,
                Collections.singletonMap("id", id),
                createUsersRowMapper()
        );

        return returnUsers(users);
    }

    private static RowMapper<Users> createUsersRowMapper() {
        return (rs, rowNum) -> new Users(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    private Optional<Users> returnUsers(final Users users) {
        if (ObjectUtils.isEmpty(users)) {
            return Optional.empty();
        }

        return Optional.of(users);
    }
}
