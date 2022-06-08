package woowacourse.shoppingcart.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.Orders;

@Repository
public class OrderDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderDetailDao orderDetailDao;

    public OrderDao(DataSource dataSource, OrderDetailDao orderDetailDao) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders")
            .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.orderDetailDao = orderDetailDao;
    }

    public Orders save(Orders orders) {
        long orderId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(orders))
            .longValue();
        orderDetailDao.saveAll(orderId, orders.getOrderDetails());
        return orders.createWithId(orderId);
    }

    public Orders findById(Long id) {
        String sql = "select * from orders where id = :id";
        return jdbcTemplate.queryForObject(sql, Map.of("id", id), getOrdersRowMapper(id));
    }

    private RowMapper<Orders> getOrdersRowMapper(Long id) {
        return (rs, rowNum) -> new Orders(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            orderDetailDao.findByOrderId(id),
            rs.getString("order_date")
        );
    }
}
