package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private static final RowMapper<CartItemEntity> CART_ITEM_ENTITY_MAPPER = (rs, rowNum) -> new CartItemEntity(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CartItemEntity findCartItemById(Long cartItemId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CART_ITEM_ENTITY_MAPPER, cartItemId);
    }

    public List<CartItemEntity> findCartItemsByCustomerId(Long customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_ENTITY_MAPPER, customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final CartItem cartItem) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItem.getProduct().getId());
            preparedStatement.setLong(3, cartItem.getQuantity());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateCartItem(Long cartItemId, CartItem newCartItem) {
        String sql = "UPDATE cart_item SET product_id = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, newCartItem.getProduct().getId(), newCartItem.getQuantity(), cartItemId);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public boolean isProductExisting(Long customerId, Long productId) {
        final String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?) as is_product_existing";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBoolean("is_product_existing"), customerId,
                        productId));
    }
}
