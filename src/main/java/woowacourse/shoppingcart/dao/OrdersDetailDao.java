package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

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
                .addValue("product_id", detail.getProductId())
                .addValue("quantity", detail.getQuantity());
    }
}
