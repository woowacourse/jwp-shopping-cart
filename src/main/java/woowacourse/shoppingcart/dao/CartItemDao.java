package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao implements CartItemRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cart> cartRowMapper = (rs, rowNum) -> new Cart(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id"),
            rs.getLong("quantity"),
            rs.getBoolean("checked"));

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cart findById(Long id) {
        final String sql = "SELECT id, customer_id, product_id, quantity, checked FROM cart_item WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, cartRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Cart> findAllByCustomerId(final Long customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity, checked FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, cartRowMapper, customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Cart findByCustomerIdAndProductId(Long customerId, Long productId) {
        try {
            final String sql = "SELECT id, customer_id, product_id, quantity, checked FROM cart_item WHERE customer_id = ? AND product_id = ?";
            return jdbcTemplate.queryForObject(sql, cartRowMapper, customerId, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final long quantity, final boolean checked) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity, checked) VALUES(?, ?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            preparedStatement.setBoolean(4, checked);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void update(Long id, Cart cart) {
        final String sql = "UPDATE cart_item SET quantity = ?, checked = ? WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, cart.getQuantity(), cart.isChecked(), id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteAllByCustomerId(Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";

        jdbcTemplate.update(sql, customerId);
    }

    public void increaseQuantityById(Long id, long quantity) {
        final String sql = "UPDATE cart_item SET quantity = quantity + ? WHERE id = ?";

        jdbcTemplate.update(sql, quantity, id);
    }
}
