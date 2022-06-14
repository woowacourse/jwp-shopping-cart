package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.utils.Fixture.customer_id없음;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import woowacourse.shoppingcart.domain.customer.Customer;

@JdbcTest
class OrderDaoTest {

    @Autowired
    private DataSource dataSource;
    private OrderDao orderDao;
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        orderDao = new OrderDao(dataSource);
        customerDao = new CustomerDao(dataSource);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        // given
        Customer save = customerDao.save(customer_id없음);

        //when
        Long orderId = orderDao.addOrders(save.getId());

        //then
        assertThat(orderId).isNotNull();
    }
}
