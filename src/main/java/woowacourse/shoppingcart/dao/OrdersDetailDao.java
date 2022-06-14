package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.entity.OrderDetailEntity;
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

    public List<OrderDetail> findAllByOrderId(final Long orderId) {
        final String sql = "SELECT P.id, P.price, P.name, P.image_url, OD.quantity " +
                "FROM orders_detail OD JOIN product P ON P.id = OD.product_id " +
                "WHERE orders_id = :orderId";
        final Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> new OrderDetail(
                rs.getLong("id"),
                rs.getInt("price"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("quantity")
        ));
    }

    public void addAllOrderDetails(final List<OrderDetailEntity> orderDetails) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) " +
                "VALUES (:orderId, :productId, :quantity)";
        namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(orderDetails));
    }
}
