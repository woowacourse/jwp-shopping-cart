package woowacourse.order.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public OrdersDetailDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders_detail")
            .usingGeneratedKeyColumns("id");
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final SqlParameterSource params = new MapSqlParameterSource("orders_id", ordersId)
            .addValue("product_id", productId)
            .addValue("quantity", quantity);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    // public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
    //     final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = ?";
    //     return jdbcTemplate.query(sql, (rs, rowNum) -> new OrderDetail(
    //             rs.getLong("product_id"),
    //             rs.getInt("quantity")
    //     ), orderId);
    // }
}
