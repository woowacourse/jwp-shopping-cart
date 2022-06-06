package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.DataNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customer_id";
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        return namedParameterJdbcTemplate.query(sql, params, ((rs, rowNum) -> rs.getLong("product_id")));
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customer_id";
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        return namedParameterJdbcTemplate.query(sql, params, ((rs, rowNum) -> rs.getLong("id")));
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
            final Map<String, Object> params = new HashMap<>();
            params.put("cartId", cartId);

            return namedParameterJdbcTemplate.queryForObject(sql, params,
                    (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(:customer_id, :product_id)";
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", productId);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        final int rowCount = namedParameterJdbcTemplate.update(sql, params);
        if (rowCount == 0) {
            throw new DataNotFoundException();
        }
    }
}
