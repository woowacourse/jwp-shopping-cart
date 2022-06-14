package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDetailDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<OrderDetail> orderDetailRowMapper =
            (rs, rowNum) -> new OrderDetail(
                    new Product(
                            rs.getLong("product_id"),
                            new ProductName(rs.getString("name")),
                            rs.getInt("price"),
                            new ImageUrl(rs.getString("image_url"))),
                    rs.getInt("quantity")
            );

    public OrderDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int saveAll(final List<OrderDetailEntity> orderDetails) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) " +
                "VALUES (:orderId, :productId, :quantity)";

        final int[] effectedRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(orderDetails));
        return Arrays.stream(effectedRows)
                .sum();
    }

    public Long save(final long orderId, final long productId, final int quantity) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orders_id", orderId);
        parameters.put("product_id", productId);
        parameters.put("quantity", quantity);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public List<OrderDetail> findOrderDetailsByOrderId(final long orderId) {
        final String sql = "SELECT p.id as product_id, p.name, p.price, p.image_url, od.quantity " +
                "FROM orders_detail od JOIN product p ON od.product_id = p.id " +
                "WHERE orders_id = :orderId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), orderDetailRowMapper);
    }

    public List<OrderDetail> findOrderDetailsByOrderIdAndCustomerId(final long orderId, final long customerId) {
        final String sql = "SELECT p.id as product_id, p.name, p.price, p.image_url, od.quantity " +
                "FROM orders_detail od JOIN orders o ON od.orders_id = o.id JOIN product p ON od.product_id = p.id " +
                "WHERE o.id = :orderId and o.customer_id=:customerId";

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("customerId", customerId);

        return namedParameterJdbcTemplate.query(sql, parameters, orderDetailRowMapper);
    }
}
