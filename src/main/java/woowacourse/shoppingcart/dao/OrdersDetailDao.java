package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class OrdersDetailDao {

    private final JdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchAddOrdersDetail(final Long ordersId, final List<Cart> carts) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final Cart cart = carts.get(i);
                final Product product = cart.getProduct();
                ps.setLong(1, ordersId);
                ps.setLong(2, product.getId());
                ps.setLong(3, cart.getQuantity());
            }

            @Override
            public int getBatchSize() {
                return carts.size();
            }
        });
    }

    public List<OrderDetail> findOrdersDetailsJoinProductByOrderId(final Long orderId) {
        final String sql =
                "SELECT od.orders_id, od.product_id, od.quantity, p.name, p.price, p.image_url FROM orders_detail AS od "
                        + "INNER JOIN product AS p ON p.id = od.product_id "
                        + "WHERE od.orders_id = ?";
        return jdbcTemplate.query(sql, rowMapper(), orderId);
    }

    public List<OrderDetail> findAllJoinProductByOrderIds(final List<Long> orderIds) {
        final String inSql = String.join(", ", Collections.nCopies(orderIds.size(), "?"));
        final String sql = String.format(
                "SELECT od.orders_id, od.product_id, od.quantity, p.name, p.price, p.image_url FROM orders_detail AS od "
                        + "INNER JOIN product AS p ON p.id = od.product_id "
                        + "WHERE od.orders_id IN (%s)", inSql);

        return jdbcTemplate.query(sql, rowMapper(), orderIds.toArray());
    }

    private RowMapper<OrderDetail> rowMapper() {
        return (rs, rowNum) -> {
            final Product product = new Product(
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url"));
            return new OrderDetail(rs.getInt("quantity"), rs.getLong("orders_id"), product);
        };
    }
}
