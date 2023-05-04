package cart.dao;

import cart.dao.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT id, email, password, created_at FROM users WHERE email = :email";

        try {
            final User user = jdbcTemplate.queryForObject(sql, Collections.singletonMap("email", email), createUsersRowMapper());
            return Optional.of(user);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password, created_at FROM users";

        return jdbcTemplate.query(sql, createUsersRowMapper());
    }

    private static RowMapper<User> createUsersRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
