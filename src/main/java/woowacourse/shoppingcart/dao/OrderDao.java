package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Long> idRowMapper = (rs, rowNum) -> rs.getLong("id");

    public OrderDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrders(final Long customerId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId ";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), idRowMapper);
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId AND id = :id";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        parameters.put("id", orderId);
        List<Long> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), idRowMapper);
        return !query.isEmpty();
    }
}
