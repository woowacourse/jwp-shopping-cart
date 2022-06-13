package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

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
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final CartItem cartItem) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, cartItem.getCustomerId());
            preparedStatement.setLong(2, cartItem.getProductId());
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

    public List<CartItem> findCartItemsByCustomerId(Long customerId) {
        final String sql = "SELECT c.id, c.customer_id, p.id as product_id, p.name, c.quantity, p.price, p.image_url " +
                "FROM cart_item as c " +
                "INNER JOIN product as p ON p.id = c.product_id " +
                "WHERE c.customer_id = ?";

        List<CartItem> query = jdbcTemplate.query(sql, (resultSet, rowNum) -> new CartItem(
                resultSet.getLong("id"),
                resultSet.getLong("customer_id"),
                resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getInt("quantity"),
                resultSet.getString("image_url")
        ), customerId);
        return query;
    }

    public void updateQuantity(Long customerId, Long productId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE product_id = ? and customer_id = ?";
        jdbcTemplate.update(sql, quantity, productId, customerId);
    }

    public void deleteCartItem(Long customerId, Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(sql, customerId, productId);
    }

    public boolean existsByCustomerIdAndProductId(Long customerId, Long productId) {
        final String sql = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id = ? and product_id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId));
    }
}
