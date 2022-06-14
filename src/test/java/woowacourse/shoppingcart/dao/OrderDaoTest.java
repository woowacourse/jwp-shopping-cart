package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final OrderDao orderDao;

    public OrderDaoTest(DataSource dataSource) {
        this.orderDao = new OrderDao(dataSource);
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
        orderDao.addOrders(customerId);
        orderDao.addOrders(customerId);

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

    @DisplayName("CustomerId에 해당하는 OrderId가 존재하는지 확인하는 기능 - 존재O")
    @Test
    void isValidOrderId_true() {
        //given
        final Long customerId = 1L;
        final Long orderId = orderDao.addOrders(customerId);

        //when
        final boolean actual = orderDao.isValidOrderId(customerId, orderId);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("CustomerId에 해당하는 OrderId가 존재하는지 확인하는 기능 - 존재X")
    @Test
    void isValidOrderId_false() {
        //given
        final Long customerId = 1L;
        final Long invalidOrderId = -1L;

        //when
        final boolean actual = orderDao.isValidOrderId(customerId, invalidOrderId);

        //then
        assertThat(actual).isFalse();
    }
}
