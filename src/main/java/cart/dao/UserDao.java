package cart.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.dao.dto.UserDto;
import cart.domain.User;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    public Integer insert(String email, String password) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }

    public List<UserDto> selectAll() {
        String sql = "SELECT id, email, password FROM USERS";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new UserDto(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password")
                )
        );
    }

    public User selectBy(Integer id) {
        String sql = "SELECT id, email, password FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password")
                ),
                id
        );
    }

    public User selectBy(String email) {
        String sql = "SELECT id, email, password FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password")
                ),
                email
        );
    }
}
