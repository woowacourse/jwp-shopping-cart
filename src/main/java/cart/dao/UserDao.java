package cart.dao;

import cart.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    public static final RowMapper<User> mapper = (rs, rowNum) -> new User(
            rs.getString("email"),
            rs.getString("password"));

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        final String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, mapper);
    }
}
