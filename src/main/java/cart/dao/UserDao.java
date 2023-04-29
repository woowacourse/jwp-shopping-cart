package cart.dao;

import cart.dao.entity.UserEntity;
import cart.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDao {

    private static final RowMapper<UserEntity> USER_ENTITY_ROW_MAPPER = (resultSet, rowNum) -> new UserEntity(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );
    private static final String[] GENERATED_ID_COLUMN = {"id"};

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserEntity> findAll() {
        final String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, USER_ENTITY_ROW_MAPPER);
    }

    public Long insert(final User user) {
        final String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteAll() {
        final String sql = "DELETE FROM users";
        jdbcTemplate.update(sql);
    }

    public UserEntity findById(Long id) {
        final String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, USER_ENTITY_ROW_MAPPER, id);
    }
}
