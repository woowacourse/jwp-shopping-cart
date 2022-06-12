package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.support.test.ExtendedJdbcTest;

@ExtendedJdbcTest
class OrderDaoTest {

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
        final Long orderId = orderDao.addOrder(customerId);

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

    @DisplayName("특정 회원에게 주어진 주문번호인지 확인한다.")
    @Test
    void isValidOrderId() {
        // given
        final Long customerId = 1L;
        final Long orderId = orderDao.addOrder(customerId);

        // when
        final boolean isValid = orderDao.isValidOrderId(customerId, orderId);

        // then
        assertThat(isValid).isTrue();
    }

}
