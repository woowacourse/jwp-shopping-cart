package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.global.DaoTest;

@Disabled
class OrderDaoTest extends DaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;

    public OrderDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        //given
        final Long customerId = 1L;

        //when
        final Long orderId = orderDao.addOrders(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("CustomerId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final Long customerId = 1L;
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);
        jdbcTemplate.update("INSERT INTO ORDERS (customer_id) VALUES (?)", customerId);

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

}
