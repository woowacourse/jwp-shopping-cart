package cart.user.dao;

import cart.user.domain.Email;
import cart.user.domain.Password;
import cart.user.domain.User;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDAO implements UserDAO {
    
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return new User(new Email(email), new Password(password));
    };
    
    public JdbcUserDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users");
    }
    
    
    @Override
    public void insert(final User user) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail().getValue())
                .addValue("password", user.getPassword().getValue());
        this.simpleJdbcInsert.execute(params);
    }
    
    @Override
    public boolean isExist(final String email) {
        final String sql = "select count(*) from users where email = ?";
        return this.jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }
    
    @Override
    public boolean isCorrectPassword(final User user) {
        final String sql = "select password from users where email = ?";
        final String password = this.jdbcTemplate.queryForObject(sql, String.class, user.getEmail().getValue());
        return Objects.equals(password, user.getPassword().getValue());
    }
    
    @Override
    public User findByEmail(final String email) {
        final String sql = "select email, password from users where email = ?";
        return this.jdbcTemplate.queryForObject(sql, this.rowMapper, email);
    }
    
    @Override
    public List<User> findAll() {
        final String sql = "select email, password from users";
        return this.jdbcTemplate.query(sql, this.rowMapper);
    }
}
