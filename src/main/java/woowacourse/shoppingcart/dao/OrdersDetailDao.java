package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrdersDetailDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public void addBatchOrderDetails(final List<OrderDetail> orderDetails) {
        simpleJdbcInsert.executeBatch(orderDetails.stream()
                .map(this::createOrderDetailParameterSource)
                .toArray(SqlParameterSource[]::new));
    }

    private MapSqlParameterSource createOrderDetailParameterSource(final OrderDetail detail) {
        return new MapSqlParameterSource("orders_id", detail.getOrderId())
                .addValue("product_id", detail.getProduct().getId())
                .addValue("quantity", detail.getQuantity());
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT o.id, o.orders_id, p.id as product_id, p.name, p.price, p.image_url, o.quantity "
                + "FROM orders_detail o "
                + "LEFT JOIN product p "
                + "ON o.product_id = p.id "
                + "WHERE orders_id = :orderId";
        final SqlParameterSource parameters = new MapSqlParameterSource("orderId", orderId);
        return namedParameterJdbcTemplate.query(sql, parameters, rowMapper());
    }

    private RowMapper<OrderDetail> rowMapper() {
        return (rs, rowNum) -> {
            final long id = rs.getLong("id");
            final long orderId = rs.getLong("orders_id");
            final long productId = rs.getLong("product_id");
            final String name = rs.getString("name");
            final int price = rs.getInt("price");
            final String imageUrl = rs.getString("image_url");
            final int quantity = rs.getInt("quantity");
            return new OrderDetail(id, orderId, new Product(productId, name, price, imageUrl), quantity);
        };
    }
}
