package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public OrderDaoTest(JdbcTemplate jdbcTemplate) {
        this.orderDao = new OrderDao(jdbcTemplate);
    }

    @DisplayName("Order를 추가하면 ID를 반환한다.")
    @Test
    void addOrders() {
        Long memberId = 1L;
        Long orderId = orderDao.addOrders(memberId);

        assertThat(orderId).isEqualTo(4);
    }

    @DisplayName("MemberId를 이용해 회원이 주문한 주문 ID를 조회한다.")
    @Test
    void findOrderIdsByMemberId() {
        Long memberId = 4L;

        List<Long> orderIds = orderDao.findOrderIdsByMemberId(memberId);

        assertThat(orderIds).contains(2L, 3L);
    }

    @DisplayName("주문 ID가 해당 회원의 주문건수면 true를 반환한다.")
    @Test
    void isExistOrderId() {
        Long memberId = 4L;
        Long orderId = 2L;
        boolean result = orderDao.isExistOrderId(memberId, orderId);

        assertThat(result).isTrue();
    }

    @DisplayName("주문 ID가 해당 회원의 주문건수가 아니면 false를 반환한다.")
    @Test
    void isNotExistOrderId() {
        Long memberId = 4L;
        Long orderId = 1L;
        boolean result = orderDao.isExistOrderId(memberId, orderId);

        assertThat(result).isFalse();
    }
}
