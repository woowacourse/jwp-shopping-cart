package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public OrderDao(final NamedParameterJdbcTemplate namedJdbcTemplate,
                    final DataSource dataSource) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrders(final Long customerId) {
        return simpleInsert.executeAndReturnKey(Map.of("customer_id", customerId)).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId ";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.query(sql, params,
                (resultSet, rowNum) -> resultSet.getLong("id"));
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = :customerId AND id = :orderId)";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "customerId", customerId,
                "orderId", orderId));
        return Boolean.TRUE.equals(namedJdbcTemplate.queryForObject(query, params, Boolean.class));
    }
}
