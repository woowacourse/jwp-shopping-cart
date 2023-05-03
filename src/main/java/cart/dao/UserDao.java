package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");
    }

    public Long insert(UserEntity userEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(userEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<UserEntity> findAll() {
        String findAllQuery = "SELECT * FROM user";

        return jdbcTemplate.query(findAllQuery, (rs, rowNum) ->
                new UserEntity(
                        rs.getLong("user_id"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
    }
}
