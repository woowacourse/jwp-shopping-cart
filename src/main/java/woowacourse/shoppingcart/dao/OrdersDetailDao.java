package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class OrdersDetailDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (:ordersId, :productId, :quantity)";
        final Map<String, Object> params = new HashMap<>();
        params.put("ordersId", ordersId);
        params.put("productId", productId);
        params.put("quantity", quantity);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :orderId";
        final Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> new OrderDetail(
                rs.getLong("product_id"),
                rs.getInt("quantity")
        ));
    }
}
