package cart.persistence.dao;

import cart.persistence.entity.UserEntity;
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
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<UserEntity> actorRowMapper = (resultSet, rowNum) -> new UserEntity(
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    public JdbcUserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_INFO")
                .usingGeneratedKeyColumns("user_id");
    }

    @Override
    public Long save(UserEntity userEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(userEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        throw new UnsupportedOperationException("지원되지 않는 기능입니다");
    }

    @Override
    public List<UserEntity> findAll() {
        final String sql = "SELECT email, password FROM user_info";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(UserEntity userEntity) {
        throw new UnsupportedOperationException("지원되지 않는 기능입니다");
    }

    @Override
    public int deleteById(long id) {
        throw new UnsupportedOperationException("지원되지 않는 기능입니다");
    }

    @Override
    public Long findUserIdByEmail(final String email) {
        final String sql = "SELECT user_id FROM user_info WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}
