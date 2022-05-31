package woowacourse.auth.dao;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.domain.User;

@Repository
public class UserDao {

    static final RowMapper<User> ROW_MAPPER = (resultSet, rowNum) ->
            new User(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUserName(String userName) {
        final String sql = "SELECT id, username, password FROM customer "
                + "WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", userName);

        return jdbcTemplate.query(sql, params, ROW_MAPPER)
                .stream()
                .findAny();
    }
}
