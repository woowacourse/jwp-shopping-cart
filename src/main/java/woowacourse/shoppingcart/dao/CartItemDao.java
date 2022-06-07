package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> rs.getLong("product_id")
        );
    }

    public List<Long> findIdsByCustomerId(final long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> rs.getLong("id")
        );
    }

    public Long findProductIdById(final long id) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(parameters), (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long findIdByCustomerIdAndProductId(long customerId, long productId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId AND product_id = :productId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        parameters.put("productId", productId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(parameters), (rs, rowNum) -> rs.getLong("id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);
        parameters.put("product_id", productId);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public int deleteById(final long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }

    public void deleteAll(final List<Long> ids) {
        final List<Map<String, Object>> batchValues = makeBatchValues(ids);

        final String sql = "DELETE FROM cart_item WHERE id = :id";

        namedParameterJdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[ids.size()]));
    }

    private List<Map<String, Object>> makeBatchValues(List<Long> ids) {
        final List<Map<String, Object>> batchValues = new ArrayList<>(ids.size());
        for (long id : ids) {
            final Map<String, Object> mapping = new HashMap<>();
            mapping.put("id", id);
            batchValues.add(mapping);
        }
        return batchValues;
    }
}
