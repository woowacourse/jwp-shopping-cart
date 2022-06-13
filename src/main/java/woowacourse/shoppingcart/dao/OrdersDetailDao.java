package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrdersDetailDao {
    private final JdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<OrderDetail> orderDetailRowMapper = ((rs, rowNum) -> {
        Long id = rs.getLong("id");
        int quantity = rs.getInt("quantity");
        Long orderId = rs.getLong("orders_id");
        Long productId = rs.getLong("product_id");
        int price = rs.getInt("price");
        String name = rs.getString("name");
        String imageUrl = rs.getString("image_url");

        return new OrderDetail(id, quantity, orderId, productId, price, name, imageUrl);
    });

    public Long save(final Long orderId, final OrderDetail orderDetail) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setLong(3, orderDetail.getQuantity());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void saveAll(final Long orderId, final List<OrderDetail> orderDetails) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderDetail orderDetail = orderDetails.get(i);
                ps.setLong(1, orderId);
                ps.setLong(2, orderDetail.getProductId());
                ps.setLong(3, orderDetail.getQuantity());
            }

            @Override
            public int getBatchSize() {
                return orderDetails.size();
            }
        });
    }

    public List<OrderDetail> findOrderDetailsByOrderIdAndCustomerId(final Long orderId, final Long customerId) {
        final String sql = "SELECT d.id, d.quantity, d.orders_id, d.product_id, p.price, p.name, p.image_url "
                + "FROM orders_detail d "
                + "INNER JOIN product p ON d.product_id = p.id "
                + "INNER JOIN orders o ON o.id = d.orders_id "
                + "WHERE d.orders_id = ? AND o.customer_id = ?";
        return jdbcTemplate.query(sql, orderDetailRowMapper, orderId, customerId);
    }

    public List<OrderDetail> findAllByCustomerId(final Long customerId) {
        final String sql = "SELECT d.id, d.quantity, d.orders_id, d.product_id, p.price, p.name, p.image_url "
                + "FROM orders_detail d "
                + "INNER JOIN product p ON d.product_id = p.id "
                + "INNER JOIN orders o ON o.id = d.orders_id "
                + "WHERE o.customer_id = ?";
        return jdbcTemplate.query(sql, orderDetailRowMapper, customerId);
    }

}
