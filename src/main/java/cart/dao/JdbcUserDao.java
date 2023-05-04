package cart.dao;

import cart.dao.entity.Users;
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

    public Optional<Users> findByEmail(final String email) {
        final String sql = "SELECT id, email, password, created_at FROM users WHERE email = :email";

        try {
            final Users users = jdbcTemplate.queryForObject(sql, Collections.singletonMap("email", email), createUsersRowMapper());
            return Optional.of(users);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Users> findAll() {
        final String sql = "SELECT id, email, password, created_at FROM users";

        return jdbcTemplate.query(sql, createUsersRowMapper());
    }

    private static RowMapper<Users> createUsersRowMapper() {
        return (rs, rowNum) -> new Users(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
