package cart.persistence.dao;

import cart.persistence.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements Dao<UserEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcUserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_INFO")
                .usingGeneratedKeyColumns("user_id");
    }

    private final RowMapper<UserEntity> actorRowMapper = (resultSet, rowNum) -> new UserEntity(
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public Long save(UserEntity userEntity) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        final String sql = "SELECT email, password FROM user_info";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(UserEntity userEntity) {
        return 0;
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public List<UserEntity> findProductsByUser(String email) {
        return null;
    }

    public Long findUserIdByEmail(final String email) {
        final String sql = "SELECT user_id FROM user_info WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}
