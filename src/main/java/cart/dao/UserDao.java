package cart.dao;

import cart.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

    private final RowMapper<User> actorRowMapper = (resultSet, rowNum) -> new User(
            resultSet.getLong("user_id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );
    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT user_id, email, password FROM USERS WHERE user_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, id)
                .stream()
                .findAny();
    }

    public List<User> findAll() {
        String sql = "SELECT user_id, email, password FROM USERS";

        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
