package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class JdbcCartItemDao implements CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long addCartItem(final int customerId, final Long productId, int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<CartItemEntity> findCartByCustomerId(int customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new CartItemEntity(
                                rs.getLong("id"),
                                rs.getInt("customer_id"),
                                rs.getLong("product_id"),
                                rs.getInt("quantity")
                        )
                , customerId);

    }

    @Override
    public boolean hasCartItem(Long cartId, int customerId) {
        final String sql = "SELECT EXISTS(SELECT id FROM cart_item WHERE id = ? and customer_id = ?)";
        return jdbcTemplate.queryForObject(sql, boolean.class, cartId, customerId);
    }

    @Override
    public void deleteCartItem(final Long cartId) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    @Override
    public boolean hasProduct(int customerId, Long productId) {
        final String sql = "SELECT EXISTS(SELECT customer_id FROM cart_item WHERE customer_id = ? and product_id = ?)";
        return jdbcTemplate.queryForObject(sql, boolean.class, customerId, productId);
    }

    @Override
    public boolean hasCart(Long cartItem, int customerId, Long productId) {
        final String sql = "SELECT EXISTS(SELECT id FROM cart_item WHERE id = ? and customer_id = ? and product_id = ?)";
        return jdbcTemplate.queryForObject(sql, boolean.class, cartItem, customerId, productId);

    }

    @Override
    public void updateCartItem(Long cartItemId, int customerId, Long productId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? "
                + "WHERE id = ? and customer_id = ? and product_id = ?";
        jdbcTemplate.update(sql, quantity, cartItemId, customerId, productId);
    }
}
