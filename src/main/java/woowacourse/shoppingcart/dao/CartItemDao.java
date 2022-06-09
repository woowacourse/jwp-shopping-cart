package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

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

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public boolean existByCustomerIdAndProductId(Long customerId, Long productId) {
        final String query = "SELECT EXISTS (SELECT id FROM cart_item where customer_id = ? and product_id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId));
    }

    public Long findIdByCustomerIdAndProductId(Long customerId, Long productId) {
        final String query = "SELECT id FROM cart_item WHERE customer_id = ? and product_id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("id"), customerId, productId);
    }

    public int findQuantityById(Long customerId) {
        final String query = "SELECT quantity FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getInt("quantity"), customerId);
    }

    public int findQuantityByCartId(Long cartId) {
        final String query = "SELECT quantity FROM cart_item WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getInt("quantity"), cartId);
    }

    public void updateProductQuantity(Long cartId, int quantity) {
        final String query = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(query, quantity, cartId);
    }
}
