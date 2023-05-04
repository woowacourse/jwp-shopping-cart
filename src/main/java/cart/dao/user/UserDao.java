package cart.dao.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private static final RowMapper<UserEntity> rowMapper =
            (rs, rowNum) -> new UserEntity(
                    rs.getLong("user_id"),
                    rs.getString("email"),
                    rs.getString("password")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");
    }

    public Long insert(UserEntity userEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(userEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<UserEntity> findAll() {
        String findAllQuery = "SELECT * FROM user";

        return jdbcTemplate.query(findAllQuery, rowMapper);
    }

    public Optional<UserEntity> findById(Long id) {
        String findByIdQuery = "SELECT * FROM user WHERE user_id = ?";

        try {
            UserEntity userEntity = jdbcTemplate.queryForObject(findByIdQuery, rowMapper, id);

            return Optional.of(userEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM user WHERE user_id = ?";

        jdbcTemplate.update(deleteByIdQuery, id);
    }
}
