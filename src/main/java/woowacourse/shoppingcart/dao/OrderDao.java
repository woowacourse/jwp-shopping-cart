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
import woowacourse.member.dto.OrderSaveServiceRequest;
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

    public Long save(final OrderSaveServiceRequest orderSaveServiceRequest) {
        final SqlParameterSource parameter = new MapSqlParameterSource("member_id", orderSaveServiceRequest.getMemberId());
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public Orders findOrderByIdLazyOrderDetails(final Long orderId) {
        String sql = "SELECT id, member_id FROM orders WHERE id = :orderId";
        final SqlParameterSource parameter = new MapSqlParameterSource("orderId", orderId);

        final Orders orders = namedParameterJdbcTemplate.queryForObject(sql, parameter, rowMapper());
        return new LazyOrders(orders.getId(), orders.getMemberId(),
                () -> namedParameterJdbcTemplate.query(LAZY_ORDER_DETAIL_QUERY, parameter, lazyRowMapper()));
    }

    public List<Orders> findOrdersByIdLazyOrderDetails(final Long memberId) {
        final List<Long> orderIds = getOrderIds(memberId);
        return orderIds.stream()
                .map(orderId -> {
                    final SqlParameterSource parameter = new MapSqlParameterSource("orderId", orderId);
                    return new LazyOrders(orderId, memberId,
                            () -> namedParameterJdbcTemplate.query(LAZY_ORDER_DETAIL_QUERY, parameter,
                                    lazyRowMapper()));
                })
                .collect(Collectors.toList());
    }

    private List<Orders> findOrdersIdsByMemberId(final Long memberId) {
        String sql = "SELECT id, member_id FROM orders WHERE member_id = :memberId";
        final SqlParameterSource parameter = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, parameter, rowMapper());
    }

    private List<Long> getOrderIds(final Long memberId) {
        return findOrdersIdsByMemberId(memberId)
                .stream().map(Orders::getId)
                .collect(Collectors.toList());
    }

    private RowMapper<Orders> rowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final Long memberId = rs.getLong("member_id");
            return new Orders(id, memberId);
        };
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
