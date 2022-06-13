package woowacourse.shoppingcart.order.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.order.domain.OrderDetail;

import java.sql.PreparedStatement;
import java.util.List;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.domain.ThumbnailImage;

@Repository
public class OrdersDetailDao {
    private final JdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql =
                "select p.id as productId, p.name as name, p.price as price, p.stock_quantity as stockQuantity, i.url as url, i.alt as alt, od.quantity as quantity "
                        + "from orders_detail as od "
                        + "inner join product as p on p.id = od.product_id "
                        + "inner join images as i on p.id = i.product_id "
                        + "where od.orders_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OrderDetail(
                new Product(rs.getLong("productId"), rs.getString("name"),
                        rs.getInt("price"), rs.getLong("stockQuantity"),
                        new ThumbnailImage(rs.getString("url"), rs.getString("alt"))
                ), rs.getInt("quantity")), orderId);
    }
}
