package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        final Long orderId = orderDao.save(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("CustomerId 집합을 이용하여 OrderId 집합을 얻는 기능")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final Long customerId = 1L;

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

}
