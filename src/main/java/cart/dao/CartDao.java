package cart.dao;

import cart.entity.Product;
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

    public Long addProductToCart(Long userId, Long productId) {
        final Map<String, Long> param = Map.of("user_id", userId, "product_id", productId);

        return (Long) simpleJdbcInsert.executeAndReturnKey(param);
    }

    public void deleteProductInCart(Long userId, Long userProductId) {
        final String sql = "DELETE FROM cart WHERE id = ? AND user_id = ?";

        jdbcTemplate.update(sql, userProductId, userId);
    }

    public List<Product> findAllProductsInCart(Long userId) {
        final String sql = "SELECT c.id, name, image, price " +
                "FROM cart c " +
                "LEFT JOIN products p ON product_id=p.id " +
                "WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Product(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)),
                userId);
    }
}
