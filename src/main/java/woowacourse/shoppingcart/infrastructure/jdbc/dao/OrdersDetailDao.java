package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrderDetail> ROW_MAPPER =
            (resultSet, rowNum) -> new OrderDetail(
                    toProduct(resultSet),
                    resultSet.getInt("quantity")
            );

    private static Product toProduct(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url"));
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public OrdersDetailDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(final Long orderId, final CartItem cartItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("orders_id", orderId);
        params.put("product_id", cartItem.getProduct().getId());
        params.put("quantity", cartItem.getQuantity());
        return jdbcInsert.executeAndReturnKey(params)
                .longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT o.product_id, o.quantity, "
                + "p.name, p.price, p.image_url FROM orders_detail o "
                + "JOIN product p ON o.product_id = p.id where o.orders_id = (:orderId)";
        final SqlParameterSource parameters = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }
}
