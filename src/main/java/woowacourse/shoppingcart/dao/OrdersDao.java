package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.OrdersDetail;

@Repository
public class OrdersDao {

    private final JdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;
    private final RowMapper<Orders> ordersRowMapper = (rs, rowNum) -> new Orders(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            findDetails(rs.getLong("id"))
    );

    public OrdersDao(final JdbcTemplate jdbcTemplate, final OrdersDetailDao ordersDetailDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = ordersDetailDao;
    }

    public Long save(final Orders orders) {
        final String sql = "INSERT INTO orders(customer_id) VALUES(?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, orders.getCustomerId());
            return preparedStatement;
        }, keyHolder);

        final long ordersId = keyHolder.getKey().longValue();
        ordersDetailDao.saveAll(orders.getOrdersDetails(), ordersId);

        return ordersId;
    }

    public Optional<Orders> findById(final Long id) {
        final String sql = "SELECT id, customer_id FROM orders WHERE id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ordersRowMapper, id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<OrdersDetail> findDetails(final Long ordersId) {
        return ordersDetailDao.findDetails(ordersId);
    }
}
