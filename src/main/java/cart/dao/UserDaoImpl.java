package cart.dao;

import cart.domain.user.User;
import cart.domain.user.UserDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<User> userRowMapper = (result, count) ->
            new User(result.getLong("id"),
                    result.getString("email"),
                    result.getString("password")
            );

    @Override
    public List<User> findAll() {
        final String query = "SELECT u.id, u.email, u.password FROM _user u";
        return jdbcTemplate.query(query, userRowMapper);
    }

    @Override
    public Optional<User> findUserByEmail(final String email) {
        final String query = "SELECT u.id, u.email, u.password FROM _user u WHERE u.email = ?";
        try {
            final User user = jdbcTemplate.queryForObject(query, userRowMapper, email);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
