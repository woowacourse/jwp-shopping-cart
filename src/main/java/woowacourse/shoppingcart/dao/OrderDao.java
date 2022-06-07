package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrders(final Long customerId) {
        final String sql = "INSERT INTO orders (customer_id) VALUES (:customerId)";
        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId";
        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customerId AND id = :orderId)";
        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("orderId", orderId);

        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

    public boolean existsOrderByCustomerId(final Long customerId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customerId)";
        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class));
    }
}
