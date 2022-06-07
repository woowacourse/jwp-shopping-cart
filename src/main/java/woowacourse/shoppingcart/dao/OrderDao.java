package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.LazyOrders;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;

@Repository
public class OrderDao {

    private static final String LAZY_ORDER_DETAIL_QUERY =
            "SELECT id, orders_id, product_id, quantity FROM orders_detail o WHERE orders_id = :orderId";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Long memberId) {
        final SqlParameterSource parameter = new MapSqlParameterSource("member_id", memberId);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public Orders findOrderByIdLazyOrderDetails(final Long orderId) {
        String sql = "SELECT id FROM orders WHERE id = :orderId";
        final SqlParameterSource parameter = new MapSqlParameterSource("orderId", orderId);

        return new LazyOrders(namedParameterJdbcTemplate.queryForObject(sql, parameter, Long.class),
                () -> namedParameterJdbcTemplate.query(LAZY_ORDER_DETAIL_QUERY, parameter, lazyRowMapper()));
    }

    private List<Long> findOrdersIdsByMemberId(final Long memberId) {
        String sql = "SELECT id FROM orders WHERE member_id = :memberId";
        final SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, parameter, rowMapper());
    }

    public List<Orders> findOrdersByIdLazyOrderDetails(final Long memberId) {
        final List<Long> orderIds = findOrdersIdsByMemberId(memberId);
        return orderIds.stream()
                .map(orderId -> {
                    final SqlParameterSource parameter = new MapSqlParameterSource("orderId", orderId);
                    return new LazyOrders(orderId,
                            () -> namedParameterJdbcTemplate.query(LAZY_ORDER_DETAIL_QUERY, parameter,
                                    lazyRowMapper()));
                })
                .collect(Collectors.toList());
    }

    private RowMapper<Long> rowMapper() {
        return (rs, rowNum) -> rs.getLong("id");
    }

    private RowMapper<OrderDetail> lazyRowMapper() {
        return (rs, rowNum) -> {
            final long id = rs.getLong("id");
            final long orderId = rs.getLong("orders_id");
            final long productId = rs.getLong("product_id");
            final int quantity = rs.getInt("quantity");
            return new OrderDetail(id, orderId, productId, quantity);
        };
    }

    public boolean isValidOrderId(final Long memberId, final Long orderId) {
        final String sql = "SELECT EXISTS(SELECT * FROM orders WHERE member_id = :memberId AND id = :orderId)";
        final SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId)
                .addValue("orderId", orderId);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, parameter, Boolean.class));
    }
}
