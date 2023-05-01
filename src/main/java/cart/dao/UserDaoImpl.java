package cart.dao;

import cart.domain.user.User;
import cart.domain.user.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<User> productRowMapper = (result, count) ->
            new User(result.getLong("id"),
                    result.getString("email"),
                    result.getString("password")
            );

    @Override
    public List<User> findAll() {
        final String query = "SELECT u.id, u.email, u.password FROM _user u";
        return jdbcTemplate.query(query, productRowMapper);
    }
}
