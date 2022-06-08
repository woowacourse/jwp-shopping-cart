package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final JdbcOrderDao orderDao;
    private final CustomerDao customerDao;

    @Autowired
    public OrderDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.orderDao = new JdbcOrderDao(jdbcTemplate);
        this.customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);

        //when
        final Long orderId = orderDao.addOrders(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("customerId를 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long orderId1 = orderDao.addOrders(customerId);
        final Long orderId2 = orderDao.addOrders(customerId);

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2).contains(orderId1, orderId2);
    }

    @DisplayName("주어진 orderId, customerId와 일치하는 Order가 있는 지 확인")
    @Test
    void hasOrderByOrderIdAndCustomerId() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long orderId1 = orderDao.addOrders(customerId);

        //when
        final boolean result = orderDao.isValidOrderId(orderId1, customerId);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("주어진 orderId, customerId와 일치하는 Order가 없는 지 확인")
    @Test
    void hasOrderByInvalidOrderIdAndCustomerId() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long orderId1 = orderDao.addOrders(customerId);

        //when
        final boolean result = orderDao.isValidOrderId(orderId1 + 1, customerId);

        //then
        assertThat(result).isFalse();
    }
}
