package woowacourse.shoppingcart.infrastructure.jdbc.dao;

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

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrderDetail> ROW_MAPPER =
            (resultSet, rowNum) -> new OrderDetail(
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity")
            );

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
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = (:orderId)";
        final SqlParameterSource parameters = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }
}
