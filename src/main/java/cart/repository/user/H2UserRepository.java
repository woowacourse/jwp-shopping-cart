package cart.repository.user;

import cart.domain.user.User;
import cart.entiy.user.UserEntity;
import cart.entiy.user.UserEntityId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2UserRepository implements UserRepository {

    private final RowMapper<UserEntity> userEntityRowMapper = (rs, rowNum) -> new UserEntity(
            new UserEntityId(rs.getLong("member_id")),
            rs.getString("email"),
            rs.getString("password"));

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public H2UserRepository(final JdbcTemplate jdbcTemplate) {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("member_id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(final User user) {
        final UserEntity userEntity = UserEntity.from(user);
        final Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("member_id", userEntity.getUserId().getValue());
        parameters.put("email", userEntity.getEmail());
        parameters.put("password", userEntity.getPassword());
        final long userId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new UserEntity(userId, userEntity).toDomain();
    }

    @Override
    public boolean existsByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM MEMBER WHERE email=? AND password=?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, email, password);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT member_id, email, password FROM MEMBER WHERE email=?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, userEntityRowMapper, email).toDomain());
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
