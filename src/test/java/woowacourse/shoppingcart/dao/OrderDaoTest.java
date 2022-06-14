package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    public OrderDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.orderDao = new OrderDao(namedParameterJdbcTemplate);
    }

    @DisplayName("주문을 저장한다.")
    @Test
    void addOrders() {
        //given
        final long customerId = 1L;

        //when
        final Long orderId = orderDao.addOrders(customerId);

        //then
        assertThat(orderId).isEqualTo(1L);
    }

    @DisplayName("해당 회원의 주문 id들을 불러온다.")
    @Test
    void findOrderIdsByCustomerId() {
        //given
        final long customerId = 1L;
        orderDao.addOrders(customerId);
        orderDao.addOrders(customerId);

        //when
        final List<Long> orderIdsByCustomerId = orderDao.findOrderIdsByCustomerId(customerId);

        //then
        assertThat(orderIdsByCustomerId).hasSize(2);
    }

}
