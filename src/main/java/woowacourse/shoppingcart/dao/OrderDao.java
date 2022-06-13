package woowacourse.shoppingcart.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public long addOrders(final long customerId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> rs.getLong("id")
        );
    }

    public Optional<Long> findOrderIdByOrderIdAndCustomerId(final long orderId, final long customerId) {
        final String sql = "SELECT id FROM orders WHERE id = :orderId and customer_id = :customerId";

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("customerId", customerId);

        final List<Long> query = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> rs.getLong("id"));

        return Optional.ofNullable(DataAccessUtils.singleResult(query));
    }
}
