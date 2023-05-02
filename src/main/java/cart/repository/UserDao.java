package cart.repository;

import cart.entity.UserEntity;
import cart.exception.AuthorizationException;
import org.springframework.dao.DataAccessException;
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

    public Integer findIdByEmailAndPassword(final String email, final String password) {
        final String sql = "select id from users where email = ? and password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        } catch (DataAccessException e) {
            throw new AuthorizationException("잘못된 인증 정보입니다.");
        }
    }

    public List<UserEntity> findAll() {
        final String sql = "select * from users";
        return jdbcTemplate.query(sql, userEntityRowMapper);
    }
}
