package cart.cart_product.dao;

import cart.cart_product.domain.CartProduct;
import cart.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcCartProductDao implements CartProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> Product.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    private final RowMapper<CartProduct> cartProductRowMapper = (resultSet, rowNum) -> CartProduct.of(
            resultSet.getLong("id"),
            resultSet.getLong("cartId"),
            resultSet.getLong("productId")
    );

    @Override
    public void insert(final Long cartId, final Long productId) {
        final String sql = "INSERT INTO cart_product(cart_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, cartId, productId);
    }

    @Override
    public int countByCartIdAndProductId(final Long cartId, final Long productId) {
        final String sql = "SELECT COUNT(*) FROM cart_product WHERE cart_id = ? AND product_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId, productId);
    }

    @Override
    public List<Product> findAllProductByCartId(final Long cartId) {
        final String sql = "SELECT p.id, p.name, p.image_url, p.price FROM cart_product as cp JOIN cart as c ON cp.cart_id = c.id JOIN product as p ON cp.product_id = p.id WHERE cart_id = ?";
        return jdbcTemplate.query(sql, productRowMapper, cartId);
    }

    @Override
    public void deleteByCartIdAndProductId(final Long cartId, final Long productId) {
        final String sql = "DELETE FROM cart_product WHERE cart_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, cartId, productId);
    }
}
