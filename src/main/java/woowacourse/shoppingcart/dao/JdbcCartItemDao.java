package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class JdbcCartItemDao implements CartItemDao {
    private static final String TABLE_NAME = "cart_item";
    private static final String ID_COLUMN = "id";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String PRODUCT_ID_COLUMN = "product_id";
    private static final String QUANTITY_COLUMN = "quantity";

    private static final RowMapper<CartItemEntity> CART_ITEM_ENTITY_MAPPER = (rs, rowNum) -> new CartItemEntity(
            rs.getLong(ID_COLUMN),
            rs.getLong(CUSTOMER_ID_COLUMN),
            rs.getLong(PRODUCT_ID_COLUMN),
            rs.getInt(QUANTITY_COLUMN)
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    public Long save(Long customerId, CartItem cartItem) {
        Map<String, Object> params = new HashMap<>();
        params.put(CUSTOMER_ID_COLUMN, customerId);
        params.put(PRODUCT_ID_COLUMN, cartItem.getProduct().getId());
        params.put(QUANTITY_COLUMN, cartItem.getQuantity());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<CartItemEntity> findById(Long cartItemId) {
        String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, CART_ITEM_ENTITY_MAPPER, cartItemId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<CartItemEntity> findAllByCustomerId(Long customerId) {
        String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_ENTITY_MAPPER, customerId);
    }

    public void update(Long cartItemId, CartItem newCartItem) {
        String sql = "UPDATE cart_item SET product_id = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, newCartItem.getProduct().getId(), newCartItem.getQuantity(), cartItemId);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM cart_item WHERE id = ?";

        int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public boolean isProductExisting(Long customerId, Long productId) {
        String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?) as is_product_existing";
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBoolean("is_product_existing"), customerId,
                        productId));
    }
}
