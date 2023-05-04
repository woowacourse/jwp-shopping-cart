package cart.dao;

import cart.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<User> actorRowMapper = (resultSet, rowNumber) -> new User(
            resultSet.getString("email"),
            resultSet.getString("password")
    );

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

    public User findBy(final Long userId) {
        final String sql = "SELECT id, email, password FROM users WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, param, actorRowMapper);
    }
}
