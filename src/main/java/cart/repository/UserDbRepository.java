package cart.repository;

import cart.domain.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDbRepository implements UserRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDbRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        RowMapper<User> userRowMapper = getUserRowMapper();

        return namedParameterJdbcTemplate.query(sql, userRowMapper);
    }

    private RowMapper<User> getUserRowMapper() {
        return (resultSet, ignored) -> {
            Long id = resultSet.getLong("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            return new User(id, email, password);
        };
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        RowMapper<User> userRowMapper = getUserRowMapper();

        User foundUser = namedParameterJdbcTemplate.queryForObject(sql, Map.of("email", email), userRowMapper);
        return Optional.ofNullable(foundUser);
    }
}
