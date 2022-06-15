package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.Quantity;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;

@Repository
public class OrdersDetailDao {

    private final RowMapper<OrderDetail> orderDetailRowMapper = (rs, rowNum) -> new OrderDetail(
            rs.getLong("product_id"),
            new Quantity(rs.getInt("quantity")),
            new Price(rs.getInt("price")),
            new Name(rs.getString("name")),
            new ImageUrl(rs.getString("image_url"))
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orders_id", ordersId);
        parameters.put("product_id", productId);
        parameters.put("quantity", quantity);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long ordersId) {
        final String sql =
                "SELECT p.id product_id, p.price price, p.name name, p.image_url image_url, od.quantity quantity "
                        + "FROM orders_detail od "
                        + "JOIN product p ON od.product_id=p.id "
                        + "WHERE orders_id = :ordersId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("ordersId", ordersId);

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), orderDetailRowMapper);
    }

    public int addAllOrdersDetails(List<Order> orders) {
        String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) " +
                "values (:orderId, :productId, :quantity)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(orders.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, batch).length;
    }
}
