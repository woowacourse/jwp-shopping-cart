package cart.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import cart.dao.entity.User;

@Repository
public class JdbcUserDao implements UserDao{

    private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcUserDao(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";
        final RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }
}
