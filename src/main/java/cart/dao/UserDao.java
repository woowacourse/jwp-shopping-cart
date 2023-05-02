package cart.dao;

import java.util.Optional;

import cart.entiy.UserEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final RowMapper<UserEntity> userEntityRowMapper = (resultSet, rowNum) -> {
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        return new UserEntity(email, password);
    };

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserEntity> findByEmail(final String email) {
        final String sql = "SELECT email, password from MEMBER where email=?";
        try {
            final UserEntity result = jdbcTemplate.queryForObject(sql, userEntityRowMapper, email);
            return Optional.of(result);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
