package cart.repository;

import cart.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<UserEntity> userEntityRowMapper = (rs, rn) ->
            new UserEntity(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name")
            );

    public UserDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    public List<UserEntity> findAll() {
        final String sql = "select * from users";
        return jdbcTemplate.query(sql, userEntityRowMapper);
    }

    public int create(final UserEntity userEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(userEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }
}
