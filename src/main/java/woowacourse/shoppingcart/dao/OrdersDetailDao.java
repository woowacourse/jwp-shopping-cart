package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public OrdersDetailDao(final NamedParameterJdbcTemplate namedJdbcTemplate,
                           final DataSource dataSource) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        return simpleInsert.executeAndReturnKey(Map.ofEntries(
                Map.entry("orders_id", ordersId),
                Map.entry("product_id", productId),
                Map.entry("quantity", quantity)
        )).longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :orderId";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of("orderId", orderId));
        return namedJdbcTemplate.query(sql, params, (resultSet, rowNum) ->
                new OrderDetail(resultSet.getLong("product_id"), resultSet.getInt("quantity")));
    }
}
