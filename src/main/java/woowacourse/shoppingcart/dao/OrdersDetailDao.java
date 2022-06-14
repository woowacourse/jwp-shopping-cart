package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.OrdersDetailEntity;

@Repository
public class OrdersDetailDao {

    private static final RowMapper<OrdersDetailEntity> ROW_MAPPER = (rs, rownum) -> new OrdersDetailEntity(
            rs.getLong("id"),
            rs.getLong("orders_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrdersDetailDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(OrdersDetailEntity ordersDetailEntity) {
        final String sql = "INSERT INTO orders_detail (orders_id, product_id, quantity) "
                + "VALUES (:ordersId, :productId, :quantity)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(ordersDetailEntity);
        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<OrdersDetailEntity> findByOrderId(final Long orderId) {
        final String sql = "SELECT id, orders_id, product_id, quantity FROM orders_detail WHERE orders_id = :id";
        SqlParameterSource source = new MapSqlParameterSource("id", orderId);
        return jdbcTemplate.query(sql, source, ROW_MAPPER);
    }
}
