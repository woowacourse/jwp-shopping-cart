package cart.dao;

import cart.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT id, email, password FROM users WHERE email = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2), rs.getString(3)), email));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(rs.getLong(1), rs.getString(2), rs.getString(3)));
    }
}
