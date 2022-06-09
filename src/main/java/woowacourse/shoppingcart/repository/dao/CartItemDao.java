package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> ROW_MAPPER = (resultSet, rowNum) -> new CartItem(
            resultSet.getLong("id"),
            resultSet.getLong("customer_id"),
            resultSet.getLong("product_id"),
            resultSet.getInt("quantity")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long insert(final Long customerId, final Long productId) {
        String query = "insert into cart_item (customer_id, product_id, quantity)"
                + " values (:customerId, :productId, 1)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);
        SqlParameterSource source = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<CartItem> selectCartItemsByCustomerId(final Long customerId) {
        String query = "select id, customer_id, product_id, quantity from cart_item where customer_id = :customerId";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public boolean existsCustomerIdAndProductId(final Long customerId, final Long productId) {
        String query = "select exists"
                + " (select id from cart_item where customer_id = :customerId and product_id = :productId)";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(query, params, Boolean.class));
    }

    public CartItem selectByCustomerIdAndProductId(final Long customerId, final Long productId) {
        String query = "select id, customer_id, product_id, quantity from cart_item"
                + " where customer_id = :customerId and product_id = :productId";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("존재하지 않는 장바구니 물품입니다.");
        }
    }

    public void updateQuantity(final Long cartItemId, final int quantity) {
        String query = "update cart_item set quantity = :quantity where id = :cartItemId";
        Map<String, Object> params = new HashMap<>();
        params.put("cartItemId", cartItemId);
        params.put("quantity", quantity);
        int affectedRowCount = namedParameterJdbcTemplate.update(query, params);
        if (affectedRowCount == 0) {
            throw new ResourceNotFoundException("존재하지 않는 장바구니 물품입니다.");
        }
    }

    public void delete(final Long cartItemId) {
        String query = "delete from cart_item where id = :cartItemId";
        Map<String, Object> params = new HashMap<>();
        params.put("cartItemId", cartItemId);
        int affectedRowCount = namedParameterJdbcTemplate.update(query, params);
        if (affectedRowCount == 0) {
            throw new ResourceNotFoundException("존재하지 않는 장바구니 물품입니다.");
        }
    }

    public void deleteAllByCustomerId(final Long customerId) {
        String query = "delete from cart_item where customer_id = :customerId";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        namedParameterJdbcTemplate.update(query, params);
    }
}
