package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@Repository
public class OrdersDetailDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertInOrdersDetail;

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsertInOrdersDetail = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(Long ordersId, CartItem cartItem) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orders_id", ordersId);
        parameterSource.addValue("product_id", cartItem.getProduct().getId());
        parameterSource.addValue("quantity", cartItem.getQuantity());
        return simpleJdbcInsertInOrdersDetail.executeAndReturnKey(parameterSource).longValue();
    }

    public List<OrderDetailEntity> findByOrderId(Long orderId) {
        final String query = "SELECT orders_detail.id, orders_detail.product_id, orders_detail.quantity"
                + " FROM orders INNER JOIN orders_detail ON orders.id = orders_detail.orders_id"
                + " WHERE orders_detail.orders_id = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> new OrderDetailEntity(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getInt("quantity")), orderId);
    }
}
