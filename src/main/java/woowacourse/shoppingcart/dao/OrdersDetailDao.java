package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.order.OrderDetail;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public OrdersDetailDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public OrderDetail addOrdersDetail(OrderDetail orderDetail) {
        long id = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(orderDetail))
                .longValue();
        return new OrderDetail(id, orderDetail.getOrdersId(), orderDetail.getProductId(), orderDetail.getQuantity());
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        String sql = "SELECT id, orders_id, product_id, quantity FROM orders_detail WHERE orders_id = :orderId";
        return jdbcTemplate.query(sql, Map.of("orderId", orderId), getOrderDetailRowMapper());
    }

    private RowMapper<OrderDetail> getOrderDetailRowMapper() {
        return (rs, rowNum) -> new OrderDetail(
                rs.getLong("id"),
                rs.getLong("orders_id"),
                rs.getLong("product_id"),
                rs.getInt("quantity")
        );
    }
}
