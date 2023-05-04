package cart.dao;

import cart.domain.cart.CartProduct;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CartDao implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<Product> productRowMapper = (result, count) ->
            new Product(result.getLong("id"),
                    result.getString("name"),
                    result.getString("image_url"),
                    result.getInt("price"),
                    ProductCategory.from(result.getString("category"))
            );

    @Override
    public Long insert(final User user, final Long productId) {
        final String query = "INSERT INTO cart(product_id, user_id) VALUES(?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setInt(1, productId.intValue());
            ps.setInt(2, user.getId().intValue());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<CartProduct> findAllByUser(final User user) {
        final String query1 = "SELECT c.id, c.product_id FROM cart c WHERE c.user_id = ?";
        final List<Map<String, Long>> cartProducts = jdbcTemplate.query(query1,
                (result, count) -> {
                    final Long id = result.getLong("id");
                    final Long productId = result.getLong("product_id");
                    return Map.of("id", id, "productId", productId);
                }, user.getId());

        final String query2 = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p WHERE p.id = ?";
        List<CartProduct> result = new ArrayList<>();
        for (Map<String, Long> cartProduct : cartProducts) {
            final Product product = jdbcTemplate.queryForObject(query2, productRowMapper, cartProduct.get("productId"));
            result.add(new CartProduct(cartProduct.get("id"), product));
        }

        return result;
    }

    @Override
    public void delete(final User user, final Long cartProductId) {
        final String query = "DELETE FROM cart c WHERE c.user_id = ? and c.id = ?";
        jdbcTemplate.update(query, user.getId(), cartProductId);
    }
}
