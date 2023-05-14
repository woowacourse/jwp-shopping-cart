package cart.auth.credential;

import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class CredentialDao {

    private static final RowMapper<Credential> credentialRowMapper = (rs, rowNum) -> new Credential(
            rs.getLong("member_id"),
            rs.getString("email"),
            rs.getString("password")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CredentialDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Credential> findByEmail(String email) {
        String sql = "select * from MEMBER where email = :email";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("email", email);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, paramSource, credentialRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

}
