package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.domain.OrderProduct;
import woowacourse.shoppingcart.domain.Product;

// TODO: single_order, order_product 테이블 별로 DAO 분리하기
// TODO: single_order -> orders, order_product -> order_detail 로 이름 변경

@Repository
public class JdbcOrderDao implements OrderDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ProductDao productDao;

    public JdbcOrderDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource, final ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("single_order")
                .usingGeneratedKeyColumns("id");
        this.productDao = productDao;
    }

    public Long save(Order order) {
        Map<String, Long> params = Map.of("customer_id", order.getCustomerId());
        Long orderId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            String sql = "INSERT INTO order_product(order_id, product_id, quantity) VALUES(?, ?, ?)";
            jdbcTemplate.update(sql, orderId, orderProduct.getProduct().getId(), orderProduct.getQuantity());
        }

        return orderId;
    }

    public Order findById(Long id) {
        String sql = "SELECT customer_id FROM single_order WHERE id = ?";
        Long customerId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("customer_id"), id);

        List<OrderProduct> orderProducts = findOrderProductsById(id);
        return new Order(orderProducts, customerId);
    }

    public List<Order> findAllByCustomerId(Long customerId) {
        String sql = "SELECT id FROM single_order WHERE customer_id = ?";
        List<Long> orderIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);

        return orderIds.stream()
                .map(this::findOrderProductsById)
                .map(orderProduct -> new Order(orderProduct, customerId))
                .collect(Collectors.toList());
    }

    private List<OrderProduct> findOrderProductsById(Long id) {
        final String sql = "SELECT product_id, quantity FROM order_product WHERE order_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = productDao.findById(rs.getLong("product_id"));
            int quantity = rs.getInt("quantity");
            return new OrderProduct(product, quantity);
        }, id);
    }

    public boolean isValidOrderId(Long customerId, Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM single_order WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}
