package cart.dao;

import cart.entity.CartItem;
import cart.entity.Product;
import cart.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public Long addProductToCart(final Long userId, final Long productId) {
        final Map<String, Long> param = Map.of("user_id", userId, "product_id", productId);

        return (Long) simpleJdbcInsert.executeAndReturnKey(param);
    }

    public void deleteProductInCart(final Long userId, final Long userProductId) {
        final String sql = "DELETE FROM cart WHERE id = ? AND user_id = ?";

        jdbcTemplate.update(sql, userProductId, userId);
    }

    public List<CartItem> findCartItemsByUserId(final Long userId) {
        final String sql = "SELECT c.id, u.id AS userId, u.email, u.password, p.id AS productId, p.name, p.image, p.price " +
                "FROM cart c " +
                "JOIN products p ON product_id = p.id " +
                "JOIN users u ON user_id = u.id " +
                "WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            final User user = new User(rs.getLong("userId"), rs.getString("email"), rs.getString("password"));
            final Product product = new Product(rs.getLong("productId"), rs.getString("name"), rs.getString("email"), rs.getInt("price"));

            return new CartItem(rs.getLong("id"), user, product);
        }, userId);
    }
}
