package woowacourse.order.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.cartitem.domain.Quantity;
import woowacourse.order.domain.OrderDetail;
import woowacourse.product.domain.Price;

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

    public Long save(final Long ordersId, final Long productId, final int quantity) {
        final SqlParameterSource params = new MapSqlParameterSource("orders_id", ordersId)
            .addValue("product_id", productId)
            .addValue("quantity", quantity);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<OrderDetail> findAllByOrderId(final Long orderId) {
        final String sql = "SELECT o.id, o.product_id, p.name, p.price, o.quantity, p.imageURL "
            + "FROM orders_detail o "
            + "LEFT JOIN product p ON o.product_id = p.id "
            + "WHERE o.orders_id = :orders_id";
        final SqlParameterSource params = new MapSqlParameterSource("orders_id", orderId);

        return jdbcTemplate.query(sql, params, rowMapper());
    }

    private RowMapper<OrderDetail> rowMapper() {
        return (rs, rowNum) -> new OrderDetail(
            rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getString("name"),
            new Price(rs.getInt("price")),
            rs.getString("imageURL"),
            new Quantity(rs.getInt("quantity"))
        );
    }
}
