package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrdersDetail(final long ordersId, final long productId, final int quantity) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orders_id", ordersId);
        parameters.put("product_id", productId);
        parameters.put("quantity", quantity);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :orderId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> new OrderDetail(
                        rs.getLong("product_id"),
                        rs.getInt("quantity"))
        );
    }
}
