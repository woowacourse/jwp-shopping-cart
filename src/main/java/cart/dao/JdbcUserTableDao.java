package cart.dao;

import cart.entity.User;
import cart.exception.NoSuchDataException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserTableDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserTableDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> readAll() {
        final String sql = "SELECT * FROM user_table";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new User(
                                rs.getLong("id"),
                                rs.getString("user_email"),
                                rs.getString("user_password"))

        );
    }

    public User findByEmail(final String userEmail) {
        final String sql = "SELECT * FROM user_table where user_email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNUm) -> {
                        return new User(
                                rs.getLong("id"),
                                rs.getString("user_email"),
                                rs.getString("user_password")
                        );
                    }, userEmail);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchDataException("해당 유저가 없습니다");
        }

    }
}
