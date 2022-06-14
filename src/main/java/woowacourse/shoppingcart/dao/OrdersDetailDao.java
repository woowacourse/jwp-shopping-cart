package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrderDetail> ORDER_DETAIL_ROW_MAPPER = (resultSet, rowNum) ->
            new OrderDetail(resultSet.getLong("product_id"),
                    resultSet.getInt("quantity"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public OrdersDetailDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        MapSqlParameterSource parameter = new MapSqlParameterSource("ordersId", ordersId)
                .addValue("productId", productId)
                .addValue("quantity", quantity);
        return insertActor.executeAndReturnKey(parameter).longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :orderId";
        MapSqlParameterSource parameter = new MapSqlParameterSource("orderId", orderId);
        return namedParameterJdbcTemplate.query(sql, parameter, ORDER_DETAIL_ROW_MAPPER);
    }
}
