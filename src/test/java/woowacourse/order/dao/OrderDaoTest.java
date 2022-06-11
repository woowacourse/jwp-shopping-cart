package woowacourse.order.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.customer.dao.CustomerDao;
import woowacourse.customer.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final OrderDao orderDao;
    private final CustomerDao customerDao;

    public OrderDaoTest(final DataSource dataSource) {
        this.orderDao = new OrderDao(dataSource);
        this.customerDao = new CustomerDao(dataSource);
    }

    @DisplayName("Order를 추가하는 기능")
    @Test
    void addOrders() {
        //given
        final Customer customer = Customer.of("username", "password1", "01011112222", "서울시");
        final Long customerId = customerDao.save(customer).getId();

        //when
        final Long orderId = orderDao.save(customerId);

        //then
        assertThat(orderId).isNotNull();
    }
}
