package cart.repository;

import cart.domain.Cart;
import cart.domain.CartProduct;
import cart.domain.Product;
import cart.domain.User;
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
    public Cart findByUser(User user) {
        String sql = "SELECT c.id, p.id as product_id, p.name as name, p.price as price, p.img_url as img_url "
                + "FROM cart_products c "
                + "JOIN products p ON(c.product_id = p.id)"
                + "WHERE cart_no = :userId";

        List<CartProduct> cartProducts = namedParameterJdbcTemplate.query(sql, Map.of("userId", user.getId()),
                getCartItemRowMapper());
        return Cart.of(user, cartProducts);
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
        String sql = "INSERT INTO cart_products(cart_no, product_id) VALUES (:userId, :productId)";
        namedParameterJdbcTemplate.update(sql,
                Map.of("userId", cart.getUser().getId(), "productId", cartProduct.getProduct().getId()));
    }
}
