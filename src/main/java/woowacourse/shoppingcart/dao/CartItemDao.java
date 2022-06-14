package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.NotFoundCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, 1);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Cart> findCartItemsByCustomerId(final Long customerId) {
        final String sql =
                "SELECT cart_item.id, cart_item.product_id, cart_item.quantity, product.name, product.price, product.image_url "
                        + "FROM cart_item "
                        + "INNER JOIN product ON cart_item.product_id = product.id "
                        + "WHERE cart_item.customer_id = ? ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Cart(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("quantity"),
                rs.getString("image_url")), customerId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Optional<Long> findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            final Long productId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
            return Optional.ofNullable(productId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateQuantity(final Long cartItemId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, cartItemId);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new NotFoundCartItemException();
        }
    }

    public void deleteCartItems(final List<Long> cartItemIds) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final Long id = cartItemIds.get(i);
                ps.setLong(1, id);
            }

            @Override
            public int getBatchSize() {
                return cartItemIds.size();
            }
        });
    }
}
