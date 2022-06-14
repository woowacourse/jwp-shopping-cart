package woowacourse.shoppingcart.infra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.OrderEntity;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Component
public class JdbcOrderDao implements OrderDao {
    private static final String FIND_ORDER_BY_ORDER_ID_SQL =
            "SELECT o.id AS order_id, "
                    + "od.id AS order_detail_id, "
                    + "o.customer_id AS customer_id, "
                    + "p.id AS product_id, "
                    + "p.name AS product_name, "
                    + "p.price AS product_price, "
                    + "p.image_url AS product_image_url, "
                    + "od.quantity AS quantity "
                    + "FROM orders o "
                    + "JOIN orders_detail od ON (od.orders_id = o.id) "
                    + "JOIN product p ON (p.id = od.product_id) ";

    private static final RowMapper<OrderEntity> ORDER_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> new OrderEntity(
                    rs.getLong("order_id"),
                    rs.getLong("order_detail_id"),
                    rs.getLong("customer_id"),
                    getProductEntity(rs),
                    rs.getInt("quantity")
            );

    private static ProductEntity getProductEntity(final ResultSet rs) throws SQLException {
        return new ProductEntity(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getLong("product_price"),
                rs.getString("product_image_url")
        );
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert ordersSimpleInsert;
    private final SimpleJdbcInsert ordersDetailSimpleInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcOrderDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersSimpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
        this.ordersDetailSimpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders_detail")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public long save(final List<CartEntity> cartEntities) {
        final long ordersId = saveOrders(cartEntities);

        final String sql = "INSERT INTO orders_detail(orders_id, product_id, quantity) VALUES(?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, getParams(ordersId, cartEntities));

        return ordersId;
    }

    private List<Object[]> getParams(final long ordersId, final List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(entity -> new Object[]{ordersId, entity.getProductEntity().getId(), entity.getQuantity()})
                .collect(Collectors.toList());
    }

    private long saveOrders(final List<CartEntity> cartEntities) {
        final long customerId = cartEntities.get(0).getCustomerId();
        return ordersSimpleInsert.executeAndReturnKey(Map.of("customer_id", customerId))
                .longValue();
    }

    @Override
    public Optional<List<OrderEntity>> findOrderById(final long orderId) {
        final String sql = FIND_ORDER_BY_ORDER_ID_SQL + " WHERE o.id = ?";

        try {
            return Optional.of(
                    jdbcTemplate.query(sql, ORDER_ENTITY_ROW_MAPPER, orderId)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<OrderEntity>> findOrdersByCustomerId(final long customerId) {
        final String sql = FIND_ORDER_BY_ORDER_ID_SQL + " WHERE o.customer_id = ?";

        try {
            return Optional.of(
                    jdbcTemplate.query(sql, ORDER_ENTITY_ROW_MAPPER, customerId)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Long> findOrderIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM orders WHERE customer_id = ? ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public boolean isValidOrderId(final Long customerId, final Long orderId) {
        final String query = "SELECT EXISTS(SELECT * FROM orders WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, orderId);
    }
}
