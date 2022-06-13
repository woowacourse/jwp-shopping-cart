package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderItem;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Order order) {
        long orderId = saveOrder(order);
        saveOrderDetails(orderId, order);
        return orderId;
    }

    private long saveOrder(Order order) {
        final String sql = "INSERT INTO orders(customer_id) VALUES(?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, order.getCustomerId());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveOrderDetails(long orderId, Order order) {
        String sql = "insert into orders_detail (orders_id, product_id, count) values (?, ?, ?)";

        List<Object[]> result = order.getOrderItems().stream()
            .map(s -> new Object[] {orderId, s.getProductId(), s.getCount()})
            .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sql, result);
    }

    public List<OrderItem> findOrderDetailsByOrderId(Long orderId) {
        String sql = "select product_id, count from orders_detail where orders_id = ?";
        return jdbcTemplate.query(sql,
            (rs, rowNum) ->
                new OrderItem(
                    rs.getLong("product_id"),
                    rs.getInt("count")
                ),
            orderId
        );
    }

    public List<Order> findAllByCustomerId(long customerId) {
        List<Long> ids = findIdsByCustomerId(customerId);
        return ids.stream()
            .map(id -> new Order(customerId, findOrderDetailsByOrderId(id)))
            .collect(Collectors.toList());
    }

    private List<Long> findIdsByCustomerId(long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }
}
