package cart.auth.dao;

import cart.auth.domain.User;
import cart.auth.dto.UserInfo;
import cart.common.exceptions.NotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDAO implements UserDAO {

    public static final String USER_DOES_NOT_EXISTS_ERROR = "존재하지 않는 유저입니다.";
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return User.of(id, email, password);
    };

    public JdbcUserDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public boolean isExist(final UserInfo userInfo) {
        final String sql = "select count(*) from users where email = ?";
        final String email = userInfo.getEmail().getValue();
        final int count = this.jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }

    @Override
    public User create(final UserInfo userInfo) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", userInfo.getEmail().getValue())
                .addValue("password", userInfo.getPassword().getValue());
        final long id = this.simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new User(id, userInfo.getEmail(), userInfo.getPassword());
    }

    @Override
    public User find(final UserInfo userInfo) {
        final String sql = "select id, email, password from users where email = ? and password = ?";
        final String email = userInfo.getEmail().getValue();
        final String password = userInfo.getPassword().getValue();
        try {
            return this.jdbcTemplate.queryForObject(sql, this.rowMapper, email, password);
        } catch (final Exception e) {
            throw new NotFoundException(USER_DOES_NOT_EXISTS_ERROR);
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "select id, email, password from users";
        return this.jdbcTemplate.query(sql, this.rowMapper);
    }

    @Override
    public void delete(final User user) {
        final String sql = "delete from users where id = ?";
        this.jdbcTemplate.update(sql, user.getId());
    }
}
