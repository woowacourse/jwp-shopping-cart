package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDetailDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<OrderDetail> orderDetailRowMapper =
            (rs, rowNum) -> new OrderDetail(
                    new Product(rs.getLong("product_id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("image_url")),
                    rs.getInt("quantity")
            );

    public OrderDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrdersDetail(final long ordersId, final long productId, final int quantity) {
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

    public List<OrderDetail> findOrderDetailsByOrderId(final long orderId) {
        final String sql = "SELECT p.id as product_id, p.name, p.price, p.image_url, od.quantity " +
                "FROM orders_detail od JOIN product p ON od.product_id = p.id " +
                "WHERE orders_id = :orderId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), orderDetailRowMapper);
    }
}
