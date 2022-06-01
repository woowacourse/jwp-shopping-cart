package woowacourse.auth.dao;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.User2;

@Repository
public class UserDao2 {

    private static final RowMapper<User2> ROW_MAPPER = (resultSet, rowNum) ->
            new User2(resultSet.getString("username"),
                    resultSet.getString("password"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao2(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User2> findByUserName(String username) {
        final String sql = "SELECT username, password FROM customer "
                + "WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream()
                .findAny();
    }
}
