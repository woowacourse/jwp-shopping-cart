package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public int findQuantity(Long productId, Long customerId) {
        final String query = "SELECT quantity FROM cart_item WHERE product_id = ? and customer_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, productId, customerId);
    }

    public Long addCartItem(final CartItem cartItem, final Long customerId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItem.getProductId());
            preparedStatement.setInt(3, cartItem.getQuantity());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateQuantity(final CartItem cartItem, final Long customerId) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE product_id = ? and customer_id = ?";
        int rowCount = jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getProductId(), customerId);
        validateRowCount(rowCount);
    }

    public void deleteByCustomer(final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }

    public void deleteCartItem(Long productId, Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE product_id = ? AND customer_id = ? ";

        final int rowCount = jdbcTemplate.update(sql, productId, customerId);
        validateRowCount(rowCount);
    }

    private void validateRowCount(int rowCount) {
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
