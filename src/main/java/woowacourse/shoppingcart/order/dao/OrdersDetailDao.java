package woowacourse.shoppingcart.order.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.product.domain.Product;

@Repository
public class OrdersDetailDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OrderDetail> rowMapper = (resultSet, rowNumber) -> {
        final Product product = new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
        return new OrderDetail(product, resultSet.getInt("quantity"));
    };

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
        final String sql = ""
                + "SELECT "
                + "p.id AS id, "
                + "p. `name` AS `name`, "
                + "p.price AS price, "
                + "p.image_url AS image_url, "
                + "od.quantity AS quantity, "
                + "FROM orders_detail AS od "
                + "INNER JOIN product AS p ON od.product_id = p.id "
                + "WHERE od.orders_id = ?";
        return jdbcTemplate.query(sql, rowMapper, orderId);
    }
}
