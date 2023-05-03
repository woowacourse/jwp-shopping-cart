package cart.repository;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.Product;
import cart.domain.User;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDbRepository implements CartRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartDbRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Cart findByUser(User user) {
        String sql = "SELECT c.id, p.id as product_id, p.name as name, p.price as price, p.img_url as img_url "
                + "FROM cart_products c "
                + "JOIN products p ON(c.product_id = p.id)"
                + "WHERE user_id = :userId";

        List<CartItem> cartItems = namedParameterJdbcTemplate.query(sql, Map.of("userId", user.getId()),
                getCartItemRowMapper());
        return Cart.of(user, cartItems);
    }

    private RowMapper<CartItem> getCartItemRowMapper() {
        return (resultSet, ignored) -> {
            Long id = resultSet.getLong("id");
            Long productId = resultSet.getLong("product_id");
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            String imgUrl = resultSet.getString("img_url");
            Product product = Product.from(productId, name, imgUrl, price);
            return new CartItem(id, product);
        };
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        String sql = "DELETE FROM cart_products WHERE id = :cardItemId";
        namedParameterJdbcTemplate.update(sql, Map.of("cardItemId", cartItemId));
    }

    @Override
    public void addCartItem(Cart cart, CartItem cartItem) {
        String sql = "INSERT INTO cart_products(user_id, product_id) VALUES (:userId, :productId)";
        namedParameterJdbcTemplate.update(sql,
                Map.of("userId", cart.getUser().getId(), "productId", cartItem.getProduct().getId()));
    }
}
