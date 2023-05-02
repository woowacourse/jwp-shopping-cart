package cart.dao;

import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> actorRowMapper = (resultSet, rowNumber) -> new User(
            resultSet.getLong("id"),
            new Email(resultSet.getString("email")),
            new Password(resultSet.getString("password"))
    );

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Optional<User> findBy(final Long id) {
        final String sql = "SELECT id, email, password FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<User> findBy(final String email) {
        final String sql = "SELECT id, email, password FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, email));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
