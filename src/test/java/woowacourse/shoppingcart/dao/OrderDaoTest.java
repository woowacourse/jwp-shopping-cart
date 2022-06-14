package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;

    public OrderDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @DisplayName("주문을 추가하는 기능")
    @Test
    void addOrders() {
        //given
        final Long customerId = 1L;

        //when
        final Long orderId = orderDao.addOrders(customerId);

        //then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("고객 id로 모든 주문 id를 가져오는 기능")
    @Test
    void findByCustomerId() {
        //given
        final Long customerId = 1L;
        final Long orderId1 = orderDao.addOrders(customerId);
        final Long orderId2 = orderDao.addOrders(customerId);

        //when
        List<Long> orderIds = orderDao.findOrderIds(customerId);

        //then
        assertThat(orderIds).containsExactly(orderId1, orderId2);
    }

    @DisplayName("고객 장바구니에 없는 아이템일 경우 false를 출력하는 기능")
    @Test
    void isValidOrderId() {
        //given
        final Long customerId = 1L;
        final Long orderId = orderDao.addOrders(customerId);

        //when
        boolean actual = orderDao.isExist(customerId, orderId + 1);

        //then
        assertThat(actual).isFalse();
    }
}
