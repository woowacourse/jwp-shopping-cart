package cart.dao;

import cart.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(rs.getLong(1), rs.getString(2), rs.getString(3)));
    }
}
