package woowacourse.shoppingcart.order.support.jdbc.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import woowacourse.shoppingcart.order.domain.OrderDetail;

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrderDetail> ROW_MAPPER =
            (resultSet, rowNum) -> new OrderDetail(
                    resultSet.getLong("orders_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getLong("quantity")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long addOrdersDetail(final OrderDetail orderDetail) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (:orderId, :productId, :quantity)";
        final SqlParameterSource parameters = new MapSqlParameterSource("orderId", orderDetail.getOrderId())
                .addValue("productId", orderDetail.getProductId())
                .addValue("quantity", orderDetail.getQuantity());
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<OrderDetail> findOrdersDetailsByOrderId(final long orderId) {
        final String sql = "SELECT orders_Id, product_id, quantity FROM orders_detail WHERE orders_id = (:orderId)";
        final SqlParameterSource parameters = new MapSqlParameterSource("orderId", orderId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }
}
