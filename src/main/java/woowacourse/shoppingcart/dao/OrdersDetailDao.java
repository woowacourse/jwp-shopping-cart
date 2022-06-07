package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.NewOrderDetail;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrderDetailEntity> ORDER_DETAIL_ENTITY_ROW_MAPPER = (resultSet, rowNum) -> new OrderDetailEntity(
        resultSet.getLong("orders_id"),
        resultSet.getLong("product_id"),
        resultSet.getInt("quantity")
    );
    private final JdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final String query = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, new String[] {"id"});
            preparedStatement.setLong(1, ordersId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String query = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> new OrderDetail(
            rs.getLong("product_id"),
            rs.getInt("quantity")
        ), orderId);
    }

    public List<OrderDetailEntity> findOrderDetailsByOrderId(long ordersId) {
        final String sql = "SELECT orders_id, product_id, quantity FROM orders_detail WHERE orders_id = ?";
        return jdbcTemplate.query(sql, ORDER_DETAIL_ENTITY_ROW_MAPPER, ordersId);
    }

    public Long add(Long orderId, NewOrderDetail orderDetail) {
        final String query = "INSERT INTO orders_detail(orders_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, new String[] {"id"});
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setLong(3, orderDetail.getQuantity());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
