package cart.auth;

import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Credential> findMemberByEmail(final String email) {
        final String sql = "SELECT * FROM member WHERE email = ?";
        try {
            final Credential member = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Credential(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            ), email);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
