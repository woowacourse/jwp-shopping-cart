package cart.dao;

import cart.entity.Product;
import cart.entity.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<User> findByEmail(final String email) {
        final String sql = "SELECT id, email, password FROM users WHERE email = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2), rs.getString(3)), email));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
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

    public void deleteProductInCart(Long userId, Long userProductId) {
        final String sql = "DELETE FROM user_product WHERE id = ? AND user_id = ?";

        jdbcTemplate.update(sql, userProductId, userId);
    }

    public List<Product> findAllProductsInCart(Long userId) {
        final String sql = "SELECT up.id, name, image, price " +
                "FROM user_product up " +
                "LEFT JOIN products p ON product_id=p.id " +
                "WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Product(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)),
                userId);
    }
}
