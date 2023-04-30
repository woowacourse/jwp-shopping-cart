package cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@JdbcTest
public abstract class JdbcSaveUser {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected long 사용자를_저장한다(final String email, final String password) {
        final String sql = "INSERT INTO users (email, password) VALUES (:email, :password)";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }
}
