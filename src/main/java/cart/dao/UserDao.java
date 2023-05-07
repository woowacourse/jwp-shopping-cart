package cart.dao;

import cart.domain.Email;
import cart.domain.Password;
import cart.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<User> actorRowMapper = (resultSet, rowNumber) -> new User.Builder()
            .id(resultSet.getLong("id"))
            .email(new Email(resultSet.getString("email")))
            .password(new Password(resultSet.getString("password")))
            .build();

    public UserDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(final User user) {
        final String sql = "INSERT INTO users(email, password) VALUES(:email, :password)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users ";
        return namedParameterJdbcTemplate.query(sql, actorRowMapper);
    }

    public Optional<User> findById(final Long userId) {
        final String sql = "SELECT id, email, password FROM users WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", userId);
        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, param, actorRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT id, email, password FROM users WHERE email = :email";
        MapSqlParameterSource param = new MapSqlParameterSource("email", email);
        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, param, actorRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void update(final User user) {
        final String sql = "UPDATE users SET email = :email, password = :password WHERE id = :id";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
