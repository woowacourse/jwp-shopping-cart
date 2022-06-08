package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@Repository
public class OrderDetailDao {

    private static final RowMapper<OrderDetailEntity> ROW_MAPPER = (resultSet, rowNum) ->
            new OrderDetailEntity(resultSet.getLong("id"),
                    resultSet.getLong("order_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDetailDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderDetailEntity> findAll() {
        final String sql = "SELECT id, order_id, product_id, quantity FROM orders_detail";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void save(List<OrderDetailEntity> orderDetails) {
        final String sql = "INSERT INTO orders_detail(order_id, product_id, quantity) "
                + "VALUES(:orderId, :productId, :quantity)";

        jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(orderDetails));
    }
}
