package cart.dao.user;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.dao.entity.User;

@Repository
public class JdbcUserDao implements UserDao {

    private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcUserDao(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Long saveUser(User user) {
        final String sql = "INSERT INTO users (email, password) VALUES (:email, :password)";
        final SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";
        final RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }
}
