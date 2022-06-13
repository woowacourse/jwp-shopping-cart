package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartItemDao {
    private static final RowMapper<CartItem> CART_ITEM_MAPPER = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var customerId = rs.getLong("customer_id");
        var productId = rs.getLong("product_id");
        var quantity = rs.getInt("quantity");
        var checked = rs.getBoolean("checked");
        return new CartItem(id, customerId, productId, quantity, checked);
    };

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findCartItemByUserId(Long customerId) {
        String sql = "SELECT * FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_MAPPER, customerId);
    }

    public boolean isCartContains(Long customerId, Long productId) {
        final var sql = "SELECT * FROM cart_item WHERE exists (SELECT id FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.query(sql, CART_ITEM_MAPPER, customerId, productId).size() > 0;
    }

    public void increaseQuantity(Long customerId, Long productId, int quantity) {
        final var sql = "UPDATE cart_item SET quantity = quantity + ? WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, quantity, customerId, productId);
    }

    public CartItem findCartItemByIds(Long customerId, Long cartId) {
        try {
            String sql = "SELECT * FROM cart_item WHERE customer_id = ? AND id = ?";
            return jdbcTemplate.queryForObject(sql, CART_ITEM_MAPPER, customerId, cartId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException("[ERROR] 해당 상품은 장바구니에 없습니다.");
        }
    }

    public void saveItemInCart(Long customerId, Long productId, int quantity) {
        final var sql = "INSERT INTO cart_item (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customerId, productId, quantity);
    }

    public void updateQuantityAndCheck(Long customerId, Long cartId, int quantity, boolean checked) {
        final var sql = "UPDATE cart_item SET quantity = ?, checked = ? WHERE customer_id = ? AND id = ?";
        jdbcTemplate.update(sql, quantity, checked, customerId, cartId);
    }

    public void deleteOneItem(Long customerId, Long cartId) {
        final var sql = "DELETE FROM cart_item WHERE customer_id = ? AND id = ?";
        jdbcTemplate.update(sql, customerId, cartId);
    }

    public void deleteCart(Long customerId) {
        final var sql = "DELETE FROM cart_item WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
}
