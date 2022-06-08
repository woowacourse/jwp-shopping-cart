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
import woowacourse.shoppingcart.domain.Product;

@Repository
public class JdbcOrderDao implements OrderDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ProductDao productDao;

    public JdbcOrderDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource, final ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
        this.productDao = productDao;
    }

    public Long save(Orders orders) {
        Map<String, Long> params = Map.of("customer_id", orders.getCustomerId());
        Long orderId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        for (OrderDetail orderDetail : orders.getOrderProducts()) {
            String sql = "INSERT INTO order_detail(order_id, product_id, quantity) VALUES(?, ?, ?)";
            jdbcTemplate.update(sql, orderId, orderDetail.getProduct().getId(), orderDetail.getQuantity());
        }

        return orderId;
    }

    public Orders findById(Long id) {
        String sql = "SELECT customer_id FROM orders WHERE id = ?";
        Long customerId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("customer_id"), id);

        List<OrderDetail> orderDetails = findOrderProductsById(id);
        return new Orders(orderDetails, customerId);
    }

    public List<Orders> findAllByCustomerId(Long customerId) {
        String sql = "SELECT id FROM orders WHERE customer_id = ?";
        List<Long> orderIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);

        return orderIds.stream()
                .map(this::findOrderProductsById)
                .map(orderProduct -> new Orders(orderProduct, customerId))
                .collect(Collectors.toList());
    }

    private List<OrderDetail> findOrderProductsById(Long id) {
        final String sql = "SELECT product_id, quantity FROM order_detail WHERE order_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = productDao.findById(rs.getLong("product_id"));
            int quantity = rs.getInt("quantity");
            return new OrderDetail(product, quantity);
        }, id);
    }

    public boolean isValidOrderId(Long customerId, Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}
