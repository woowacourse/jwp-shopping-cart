package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.entity.OrdersEntity;

@Repository
public class JdbcOrdersDao implements OrdersDao {
    private static final String TABLE_NAME = "orders";
    private static final String ID_COLUMN = "id";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";

    private static final RowMapper<OrdersEntity> ORDERS_ENTITY_ROW_MAPPER = (rs, rowNum) -> new OrdersEntity(
            rs.getLong(ID_COLUMN),
            rs.getLong(CUSTOMER_ID_COLUMN)
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcOrdersDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    public Long save(Orders orders) {
        Map<String, Long> params = Map.of(CUSTOMER_ID_COLUMN, orders.getCustomerId());
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<OrdersEntity> findById(Long id) {
        String sql = "SELECT id, customer_id FROM Orders WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, ORDERS_ENTITY_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<OrdersEntity> findAllByCustomerId(Long customerId) {
        String sql = "SELECT id, customer_id FROM orders WHERE customer_id = ?";
        return jdbcTemplate.query(sql, ORDERS_ENTITY_ROW_MAPPER, customerId);
    }
}
