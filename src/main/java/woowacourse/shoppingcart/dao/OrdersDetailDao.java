package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
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
        Long productId = rs.getLong("product_id");
        int price = rs.getInt("price");
        String name = rs.getString("name");
        String imageUrl = rs.getString("image_url");

        return new OrderDetail(id, quantity, productId, price, name, imageUrl);
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

    public List<OrderDetail> findOrderDetailsByOrderIdAndCustomerId(final Long orderId, final Long customerId) {
        final String sql = "SELECT d.id, d.quantity, d.product_id, p.price, p.name, p.image_url "
                + "FROM orders_detail d "
                + "INNER JOIN product p ON d.product_id = p.id "
                + "INNER JOIN orders o ON o.id = d.orders_id "
                + "WHERE d.orders_id = ? AND o.customer_id = ?";
        return jdbcTemplate.query(sql, orderDetailRowMapper, orderId, customerId);
    }
}
