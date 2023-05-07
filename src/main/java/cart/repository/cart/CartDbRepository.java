package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDbRepository implements CartRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartDbRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Cart findByNo(Long cartNo) {
        String sql = "SELECT c.id, p.id as product_id, p.name as name, p.price as price, p.img_url as img_url "
                + "FROM cart_products c "
                + "JOIN products p ON(c.product_id = p.id)"
                + "WHERE cart_no = :cartNo";

        List<CartProduct> cartProducts = namedParameterJdbcTemplate.query(sql, Map.of("cartNo", cartNo),
                getCartItemRowMapper());
        return new Cart(cartNo, cartProducts);
    }

    private RowMapper<CartProduct> getCartItemRowMapper() {
        return (resultSet, ignored) -> {
            Long id = resultSet.getLong("id");
            Long productId = resultSet.getLong("product_id");
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            String imgUrl = resultSet.getString("img_url");
            Product product = Product.from(productId, name, imgUrl, price);
            return new CartProduct(id, product);
        };
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        String sql = "DELETE FROM cart_products WHERE id = :cardItemId";
        namedParameterJdbcTemplate.update(sql, Map.of("cardItemId", cartItemId));
    }

    @Override
    public void addCartItem(Cart cart, CartProduct cartProduct) {
        String sql = "INSERT INTO cart_products(cart_no, product_id) VALUES (:cartNo, :productId)";
        namedParameterJdbcTemplate.update(sql,
                Map.of("cartNo", cart.getCartNo(), "productId", cartProduct.getProduct().getId()));
    }
}
