package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@Repository
public class JdbcOrderDetailDao implements OrderDetailDao {
    private static final String TABLE_NAME = "order_detail";
    private static final String KEY_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<OrderDetailEntity> orderDetailRowMapper = (rs, rowNum) -> new OrderDetailEntity(
            rs.getLong("id"),
            rs.getLong("order_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    public JdbcOrderDetailDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN);
    }

    @Override
    public Long save(OrderDetail orderDetail, long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderId);
        params.put("product_id", orderDetail.getProduct().getId());
        params.put("quantity", orderDetail.getQuantity());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<OrderDetailEntity> findAllByOrderId(Long orderId) {
        final String sql = "SELECT id, order_id, product_id, quantity FROM order_detail WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderDetailRowMapper, orderId);
    }
}
