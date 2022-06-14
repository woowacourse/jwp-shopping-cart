package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Integer findQuantityById(Long cartId) {
        final String sql = "SELECT quantity FROM cart_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId);
    }

    public void addCartItem(Cart cart) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, cart.getCustomerId(), cart.getProductId(), cart.getQuantity());
    }

    public void deleteCartItem(final Long customerId, Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? AND product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(Cart cart) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? AND product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, cart.getQuantity(), cart.getCustomerId(), cart.getProductId());
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
