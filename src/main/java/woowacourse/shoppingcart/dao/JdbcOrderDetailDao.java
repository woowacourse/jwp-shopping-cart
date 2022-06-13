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
    private static final String ID_COLUMN = "id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String PRODUCT_ID_COLUMN = "product_id";
    private static final String QUANTITY_COLUMN = "quantity";

    private static final RowMapper<OrderDetailEntity> ORDER_DETAIL_ENTITY_ROW_MAPPER = (rs, rowNum) -> new OrderDetailEntity(
            rs.getLong(ID_COLUMN),
            rs.getLong(ORDER_ID_COLUMN),
            rs.getLong(PRODUCT_ID_COLUMN),
            rs.getInt(QUANTITY_COLUMN)
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcOrderDetailDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public Long save(OrderDetail orderDetail, long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put(ORDER_ID_COLUMN, orderId);
        params.put(PRODUCT_ID_COLUMN, orderDetail.getProduct().getId());
        params.put(QUANTITY_COLUMN, orderDetail.getQuantity());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<OrderDetailEntity> findAllByOrderId(Long orderId) {
        String sql = "SELECT id, order_id, product_id, quantity FROM order_detail WHERE order_id = ?";
        return jdbcTemplate.query(sql, ORDER_DETAIL_ENTITY_ROW_MAPPER, orderId);
    }
}
