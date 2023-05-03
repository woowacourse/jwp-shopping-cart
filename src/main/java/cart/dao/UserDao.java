package cart.dao;

import cart.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserEntity> findAll() {
        String sql = "SELECT * FROM `USER`";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new UserEntity(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"))
        );
    }

    public UserEntity findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM `USER` WHERE email=? AND password=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new UserEntity(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"))
                , email, password
        );
    }
}
