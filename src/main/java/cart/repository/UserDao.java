package cart.repository;

import cart.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<UserEntity> userEntityRowMapper = (rs, rn) ->
            new UserEntity(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name")
            );

    public UserDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<UserEntity> findAll() {
        final String sql = "select * from users";
        return jdbcTemplate.query(sql, userEntityRowMapper);
    }
}
