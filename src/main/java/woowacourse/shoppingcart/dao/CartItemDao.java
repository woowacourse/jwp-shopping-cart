package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItemEntity> CART_ITEM_ENTITY_ROW_MAPPER = (rs, rowNum) -> new CartItemEntity(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity"));

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(final NamedParameterJdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String query = "SELECT product_id FROM cart_item WHERE customer_id = :customer_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        try {
            return jdbcTemplate.query(query, params, (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<CartItemEntity> findAllByCustomerId(final Long customerId) {
        final String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = :customer_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        try {
            return jdbcTemplate.query(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public CartItemEntity findByCustomerIdAndProductId(final Long customerId, final Long productId) {
        final String query = "SELECT id, customer_id, product_id, quantity FROM cart_item"
                + " WHERE customer_id = :customer_id AND product_id = :product_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", productId);

        try {
            return jdbcTemplate.queryForObject(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public CartItemEntity findById(final Long cartId) {
        final String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = :id";

        final Map<String, Object> params = new HashMap<>();
        params.put("id", cartId);

        try {
            return jdbcTemplate.queryForObject(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public boolean existCartItem(final Long customerId, final Long productId) {
        final String query = "SELECT exists (SELECT * FROM cart_item"
                + " where customer_id = :customer_id AND product_id = :product_id)";

        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", productId);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, params, Boolean.class));
    }

    public Long save(final Long customerId, final Long productId, final int quantity) {
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", productId);
        params.put("quantity", quantity);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void updateQuantity(final Long customerId, final Long productId, final int quantity) {
        final String query = "UPDATE cart_item set quantity = :quantity"
                + " WHERE customer_id = :customer_id AND product_id = :product_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("quantity", quantity);
        params.put("customer_id", customerId);
        params.put("product_id", productId);

        jdbcTemplate.update(query, params);
    }

    public void delete(final Long id) {
        final String query = "DELETE FROM cart_item WHERE id = :id";

        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        jdbcTemplate.update(query, params);
    }
}
