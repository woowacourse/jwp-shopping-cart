package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrdersDetailDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrdersDetailDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ORDERS_DETAIL")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Long orderId, OrderDetail orderDetail) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orders_id", orderId);
        parameters.put("product_id", orderDetail.getProductId());
        parameters.put("quantity", orderDetail.getQuantity());

        simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Long findProductIdByOrderId(Long orderId) {
        String sql = "SELECT product_id FROM ORDERS_DETAIL WHERE orders_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, orderId);
    }

    public Integer findQuantityByOrderId(Long orderId) {
        String sql = "SELECT quantity FROM ORDERS_DETAIL WHERE orders_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, orderId);
    }
}
