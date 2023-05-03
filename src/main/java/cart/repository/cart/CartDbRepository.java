package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartItems;
import cart.domain.member.Member;
import cart.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartDbRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDbRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public boolean existCart(Member member) {
        String sql = "SELECT COUNT(*) FROM cart WHERE member_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, member.getId());
        return count > 0;
    }

    @Override
    public Cart findCartByMember(final Member member) {
        String sql = "SELECT id FROM cart WHERE member_id = ?";
        Long cartId = jdbcTemplate.queryForObject(sql, Long.class, member.getId());

        List<Product> products = findAllProductsByCartId(cartId);
        CartItems cartItems = new CartItems(products);

        return new Cart(cartId, member, cartItems);
    }

    private List<Product> findAllProductsByCartId(Long cartId) {
        String sql = "SELECT p.* FROM product p JOIN cart_item ci ON p.id = ci.product_id WHERE ci.cart_id = ?";
        return jdbcTemplate.query(sql, getProductRowMapper(), cartId);
    }

    @Override
    public void createCart(final Cart cart) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", cart.getMember().getId());
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        key.longValue();
    }

    @Override
    public Long saveCartItem(final Cart cart) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");

        Product product = cart.getLastCartItem();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cart_id", cart.getId());
        parameters.put("product_id", product.getId());

        Long cartItemId = insert.executeAndReturnKey(parameters).longValue();
        return cartItemId;
    }

    @Override
    public void deleteCartItem(Cart cart, Product product) {
        String findCartItemSql = "SELECT ci.id FROM cart_item ci " +
                "JOIN cart c ON ci.cart_id = c.id " +
                "WHERE c.id = ? AND ci.product_id = ?";

        Long cartItemId = jdbcTemplate.queryForObject(findCartItemSql, Long.class, cart.getId(), product.getId());

        if (cartItemId != null) {
            String deleteCartItemSql = "DELETE FROM cart_item WHERE id = ?";
            jdbcTemplate.update(deleteCartItemSql, cartItemId);
        }
    }

    private RowMapper<Product> getProductRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imgUrl = rs.getString("img_url");

            return Product.from(id, name, imgUrl, price);
        };
    }
}
