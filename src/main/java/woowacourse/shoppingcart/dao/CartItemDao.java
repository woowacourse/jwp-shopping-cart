package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Integer findCountById(Long cartId) {
        try {
            final String sql = "SELECT count FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("count"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final int count) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, count);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(final long customerId, final long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
