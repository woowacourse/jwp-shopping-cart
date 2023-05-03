package cart.dao;

import cart.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_product")
                .usingGeneratedKeyColumns("id");
    }

    public List<User> findAll() {
        final String sql = "SELECT id, email, password FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(rs.getLong(1), rs.getString(2), rs.getString(3)));
    }

    public Long addProductToCart(Long userId, Long productId) {
        final Map<String, Long> param = Map.of("user_id", userId, "product_id", productId);

        return (Long) simpleJdbcInsert.executeAndReturnKey(param);
    }
}
