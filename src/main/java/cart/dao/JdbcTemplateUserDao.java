package cart.dao;

import cart.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateUserDao implements UserDao {

    private final SimpleJdbcInsert insertUsers;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserDao(JdbcTemplate jdbcTemplate) {
        this.insertUsers = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(final UserEntity user) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        return insertUsers.executeAndReturnKey(parameters).intValue();
    }

    @Override
    public List<UserEntity> selectAll() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql, userEntityRowMapper);
    }

    private final RowMapper<UserEntity> userEntityRowMapper = (resultSet, rowNumber) -> {
        UserEntity userEntity = new UserEntity(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return userEntity;
    };

    @Override
    public int update(final UserEntity user) {
        String sql = "update users set (email, password) = (?, ?) where id = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public int delete(int userId) {
        String sql = "delete from users where id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}
