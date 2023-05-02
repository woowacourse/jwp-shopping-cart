package cart.dao;

import cart.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUsersDao implements UsersDao {

    private static final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getString("email"), rs.getString("password"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcUsersDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM users;";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User findByEmail(final String email) {
        final String sql = "SELECT * FROM users WHERE email=?;";
        return jdbcTemplate.queryForObject(sql, userRowMapper, email);
    }
}
