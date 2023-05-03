package cart.persistence.dao;

import cart.domain.Product;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CartItemWithProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemWithProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findProductsByCartId(Long cartId) {
        String sql = "SELECT p.id, p.name, p.image_url, p.price FROM cart_item as ci JOIN product as p on ci.product_id = p.id WHERE cart_id = :cart_id";

        var parameterSource = new MapSqlParameterSource("cart_id", cartId);

        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> {
            Long productId = rs.getLong("id");
            String name = rs.getString("name");
            String imageUrl = rs.getString("image_url");
            BigDecimal price = rs.getBigDecimal("price");
            return new Product(productId, name, imageUrl, price);
        });
    }
}
