package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate,
                       NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<CartItemEntity> findAllByCustomerId(final Long customerId) {
        final String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = :customer_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        try {
            return namedParameterJdbcTemplate
                    .query(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
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
            return namedParameterJdbcTemplate
                    .queryForObject(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public CartItemEntity findById(final Long cartId) {
        final String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = :id";

        final Map<String, Object> params = new HashMap<>();
        params.put("id", cartId);

        try {
            return namedParameterJdbcTemplate
                    .queryForObject(query, params, CART_ITEM_ENTITY_ROW_MAPPER);
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

        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(query, params, Boolean.class));
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void updateCartItem(final Long customerId, final Long productId, final int quantity) {
        final String query = "UPDATE cart_item set quantity = :quantity"
                + " WHERE customer_id = :customer_id AND product_id = :product_id";

        final Map<String, Object> params = new HashMap<>();
        params.put("quantity", quantity);
        params.put("customer_id", customerId);
        params.put("product_id", productId);

        namedParameterJdbcTemplate.update(query, params);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
