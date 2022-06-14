package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate,
                       NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    public boolean existByCustomerIdAndProductId(Long customerId, Long productId) {
        final String query = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
    }

    public void deleteCartItem(final Long customerId, Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? AND product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItems(final Long customerId, List<Long> productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :CUSTOMER_ID AND product_id in (:PRODUCT_ID)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("CUSTOMER_ID", customerId);
        parameterSource.addValue("PRODUCT_ID", productId);
        int rowCount = namedParameterJdbcTemplate.update(sql, parameterSource);
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
