package cart.settings.dao;

import cart.settings.domain.User;
import cart.settings.dto.UserRequestDTO;
import cart.settings.exceptions.NotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDAO implements UserDAO {

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
    public boolean isExist(final UserRequestDTO userRequestDTO) {
        final String sql = "select count(*) from users where email = ?";
        final String email = userRequestDTO.getEmail().getValue();
        final int count = this.jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }

    @Override
    public User create(final UserRequestDTO userRequestDTO) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", userRequestDTO.getEmail().getValue())
                .addValue("password", userRequestDTO.getPassword().getValue());
        final long id = this.simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new User(id, userRequestDTO.getEmail(), userRequestDTO.getPassword());
    }

    @Override
    public User find(final UserRequestDTO userRequestDTO) {
        final String sql = "select id, email, password from users where email = ? and password = ?";
        final String email = userRequestDTO.getEmail().getValue();
        final String password = userRequestDTO.getPassword().getValue();
        try {
            return this.jdbcTemplate.queryForObject(sql, this.rowMapper, email, password);
        } catch (final Exception e) {
            throw new NotFoundException("User");
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
