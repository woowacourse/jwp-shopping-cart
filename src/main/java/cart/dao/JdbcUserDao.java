package cart.dao;

import cart.domain.user.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<User> userRowMapper = (resultSet, rowNum) ->
            new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );

    public JdbcUserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_list")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long insert(final User user) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public boolean isExist(final long id) {
        final String sql = "SELECT EXISTS (SELECT 1 FROM user_list WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    @Override
    public Optional<User> findById(final long id) {
        final String sql = "SELECT * FROM user_list WHERE id = ?";
        try {
            final User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            return Optional.of(user);
        } catch (final IncorrectResultSizeDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT * FROM user_list WHERE email = ?";
        try {
            final User user = jdbcTemplate.queryForObject(sql, userRowMapper, email);
            return Optional.of(user);
        } catch (final IncorrectResultSizeDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM user_list ORDER BY id ASC";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
