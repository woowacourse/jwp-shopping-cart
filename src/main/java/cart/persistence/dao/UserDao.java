package cart.persistence.dao;

import cart.persistence.entity.UserEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(final UserEntity userEntity) {
        final String query = "INSERT INTO users(email, password, nickname, telephone) VALUES (?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, userEntity.getEmail());
            ps.setString(2, userEntity.getPassword());
            ps.setString(3, userEntity.getNickname());
            ps.setString(4, userEntity.getTelephone());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<UserEntity> findById(final Long id) {
        final String query = "SELECT u.id, u.email, u.password, u.nickname, u.telephone FROM users u WHERE u.id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, userRowMapper(), id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<UserEntity> userRowMapper() {
        return (result, count) ->
                new UserEntity(result.getLong("id"), result.getString("email"),
                        result.getString("password"), result.getString("nickname"),
                        result.getString("telephone"));
    }
}
