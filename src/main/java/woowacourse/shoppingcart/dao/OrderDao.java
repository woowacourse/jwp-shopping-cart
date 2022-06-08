package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.Orders;

@Repository
public class OrderDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final OrderDetailDao orderDetailDao;

    public OrderDao(DataSource dataSource, OrderDetailDao orderDetailDao) {
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("orders")
            .usingGeneratedKeyColumns("id");
        this.orderDetailDao = orderDetailDao;
    }

    public Orders save(Orders orders) {
        long orderId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(orders))
            .longValue();
        orderDetailDao.saveAll(orderId, orders.getOrderDetails());
        return orders.createWithId(orderId);
    }
}
