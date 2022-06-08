package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public OrderDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrder(final Long customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("order_date", LocalDateTime.now());
        return jdbcInsert.executeAndReturnKey(params)
                .longValue();
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = (:customerId) AND id = (:orderId))";

        final SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId)
                .addValue("orderId", orderId);
        return jdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }
}
