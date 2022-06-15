package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.cartItem.ItemNotExistedInCartBadRequestException;
import woowacourse.shoppingcart.exception.cartItem.ShoppingCartNotFoundCartItemException;

import java.sql.PreparedStatement;
import java.util.List;

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
            throw new ShoppingCartNotFoundCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new ShoppingCartNotFoundCartItemException();
        }
    }

    public int findQuantityByProductIdAndCustomerId(Long productId, Long customerId) {
        final String query = "SELECT quantity FROM cart_item WHERE customer_id = ? and product_id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getInt("quantity"), customerId, productId);
    }

    public boolean existByProductIdAndCustomerId(Long productId, Long customerId) {
        final String query = "SELECT EXISTS(SELECT id FROM cart_item WHERE product_id = ? and customer_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, productId, customerId);
    }

    public void deleteByProductIdAndCustomerId(final Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item where customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new ItemNotExistedInCartBadRequestException();
        }
    }

    public void updateQuantity(final Long customerId, final Long productId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, quantity, customerId, productId);
        if (rowCount == 0) {
            throw new ItemNotExistedInCartBadRequestException();
        }
    }
}
