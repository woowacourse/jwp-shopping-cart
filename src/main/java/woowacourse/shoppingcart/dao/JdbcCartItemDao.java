package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.notfound.CartItemNotFoundException;

@Repository
public class JdbcCartItemDao implements CartItemDao {
    private static final RowMapper<CartItemEntity> CART_ITEM_ENTITY_MAPPER = (rs, rowNum) -> new CartItemEntity(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Long customerId, final CartItem cartItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", cartItem.getProduct().getId());
        params.put("quantity", cartItem.getQuantity());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public CartItemEntity findById(Long cartItemId) {
        try {
            final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, CART_ITEM_ENTITY_MAPPER, cartItemId);
        } catch (EmptyResultDataAccessException e) {
            throw new CartItemNotFoundException();
        }
    }

    public List<CartItemEntity> findAllByCustomerId(Long customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_ENTITY_MAPPER, customerId);
    }

    public void update(Long cartItemId, CartItem newCartItem) {
        String sql = "UPDATE cart_item SET product_id = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, newCartItem.getProduct().getId(), newCartItem.getQuantity(), cartItemId);
    }

    public void delete(final Long id) {
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
