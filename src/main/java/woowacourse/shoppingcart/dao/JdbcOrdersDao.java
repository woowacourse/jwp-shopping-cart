package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

@Repository
public class JdbcOrdersDao implements OrdersDao {
    private static final String TABLE_NAME = "orders";
    private static final String KEY_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final OrderDetailDao orderDetailDao;

    public JdbcOrdersDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource, final ProductDao productDao,
                         OrderDetailDao orderDetailDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN);
        this.orderDetailDao = orderDetailDao;
    }

    public Long save(Orders orders) {
        Map<String, Long> params = Map.of("customer_id", orders.getCustomerId());
        long orderId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        for (OrderDetail orderDetail : orders.getOrderDetails()) {
            orderDetailDao.save(orderId, orderDetail.getProduct().getId(), orderDetail.getQuantity());
        }

        return orderId;
    }

    public Orders findById(Long id) {
        String sql = "SELECT customer_id FROM orders WHERE id = ?";
        Long customerId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("customer_id"), id);

        List<OrderDetail> orderDetails = findOrderDetailsById(id);
        return new Orders(orderDetails, customerId);
    }

    public List<Orders> findAllByCustomerId(Long customerId) {
        String sql = "SELECT id FROM orders WHERE customer_id = ?";
        List<Long> orderIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);

        return orderIds.stream()
                .map(this::findOrderDetailsById)
                .map(orderProduct -> new Orders(orderProduct, customerId))
                .collect(Collectors.toList());
    }

    private List<OrderDetail> findOrderDetailsById(Long id) {
        return orderDetailDao.findAllByOrderId(id);
    }

    public boolean isValidOrderId(Long customerId, Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}
