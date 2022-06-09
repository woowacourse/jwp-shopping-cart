package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public OrderDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrders(final Long customerId) {
        MapSqlParameterSource parameter = new MapSqlParameterSource("customerId", customerId);
        return insertActor.executeAndReturnKey(parameter).longValue();
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = :customerId";
        MapSqlParameterSource parameter = new MapSqlParameterSource("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, parameter, (rs, rowNum) -> rs.getLong("id"));
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS (SELECT 1 FROM orders WHERE customer_id = :customerId AND id = :orderId)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId)
                .addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForObject(query, parameters, Integer.class) != 0;
    }
}
