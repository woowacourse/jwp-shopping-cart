package cart.dao;

import cart.entity.User;
import cart.exception.UserAuthorizationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, email);
        } catch (EmptyResultDataAccessException exception) {
            throw new UserAuthorizationException("입력된 email을 사용하는 사용자를 찾을 수 없습니다. 입력된 email : " + email);
        }
    }
}
