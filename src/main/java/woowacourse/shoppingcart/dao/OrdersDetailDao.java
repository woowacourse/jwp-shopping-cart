package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.OrderDetail;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OrdersDetailDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) VALUES (:orderid, :productid, :quantity)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("orderid", ordersId);
        sqlParameterSource.addValue("productid", productId);
        sqlParameterSource.addValue("quantity", quantity);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }


    public List<OrderDetail> findOrdersDetailsByOrderId(final Long orderId) {
        final String sql = "SELECT product_id, quantity FROM orders_detail WHERE orders_id = :orderid";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("orderid", orderId);

        return namedParameterJdbcTemplate.query(sql, parameterSource, rowMapper());
    }

    private RowMapper<OrderDetail> rowMapper() {
        return (rs, rowNum) -> new OrderDetail(
                rs.getLong("product_id"),
                rs.getInt("quantity")
        );
    }
}
