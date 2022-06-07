package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

import java.sql.PreparedStatement;
import java.util.List;
import woowacourse.shoppingcart.entity.OrdersDetailEntity;

@Repository
public class JdbcOrdersDetailDao implements OrdersDetailDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcOrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, ordersId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<OrdersDetailEntity> findOrdersDetailsByOrderId(Long orderId) {
        final String sql = "SELECT id, orders_id, product_id, quantity FROM orders_detail WHERE orders_id = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new OrdersDetailEntity(
                                rs.getLong("id"),
                                rs.getLong("orders_id"),
                                rs.getLong("product_id"),
                                rs.getInt("quantity")
                        )
                , orderId);
    }

}
