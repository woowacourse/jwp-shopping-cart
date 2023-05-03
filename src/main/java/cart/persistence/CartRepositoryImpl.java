package cart.persistence;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartRepository;
import cart.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.List;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cart save(Cart cart) {
        Long cartId = cart.getCartId();

        List<CartItem> cartItems = cart.getCartItems();
        String insertItem = "INSERT IGNORE INTO cart_item(cart_item_id, cart_id, product_id) VALUES(?, ?, ?)";
        for (CartItem item : cartItems) {
            jdbcTemplate.update(insertItem, item.getCartItemId(), cartId, item.getProduct().getId());
        }

        return cart;
    }

    @Override
    public Cart getCartByMemberId(Long memberId) {
        String sql = "SELECT cart_id FROM cart WHERE member_id = ?";

        Long cartId;
        try {
            cartId = jdbcTemplate.queryForObject(sql, Long.class, memberId);
        } catch (EmptyResultDataAccessException e) {
            String insert = "INSERT INTO cart(member_id) VALUES(?)";

            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                var psmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                psmt.setLong(1, memberId);
                return psmt;
            }, keyHolder);
            cartId = (Long) keyHolder.getKey().longValue();
        }

        String sql2 = "SELECT ci.cart_item_id, p.id, p.name, p.image_url, p.price FROM cart_item as ci JOIN product as p on ci.product_id = p.id WHERE cart_id = ?";

        List<CartItem> cartItems = jdbcTemplate.query(sql2, (rs, rowNum) -> {
            Long cartItemId = rs.getLong("cart_item_id");
            Long productId = rs.getLong("id");
            String name = rs.getString("name");
            String imageUrl = rs.getString("image_url");
            BigDecimal price = rs.getBigDecimal("price");
            Product product = new Product(productId, name, imageUrl, price);
            return new CartItem(cartItemId, product);
        }, cartId);

        return new Cart(cartId, memberId, cartItems);
    }
}
