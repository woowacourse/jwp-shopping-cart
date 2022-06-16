package woowacourse.shoppingcart.unit.order.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.unit.DaoTest;

class OrderDaoTest extends DaoTest {

    @Test
    @DisplayName("Customer Id가 주어지면 새로운 Order를 생성한다.")
    void addOrders() {
        // given
        final Long customerId = 1L;

        // when
        final Long actual = orderDao.addOrders(customerId);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Customer Id에 해당하는 모든 Order를 조회한다.")
    void findOrderIdsByCustomerId() {
        // given
        final Long customerId = 2L;
        final List<Long> expected = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final Long orderId = orderDao.addOrders(customerId);
            expected.add(orderId);
        }

        // when
        final List<Long> actual = orderDao.findOrderIdsByCustomerId(customerId);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Customer의 Id와 Order의 Id가 모두 일치하는 Order의 존재하면 true를 반환한다.")
    void isValidOrderId_existOrder_trueReturned() {
        // given
        final Long customerId = 1L;
        final Long orderId = orderDao.addOrders(customerId);

        // when
        final boolean actual = orderDao.isValidOrderId(customerId, orderId);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Customer의 Id와 Order의 Id가 모두 일치하는 Order가 존재하지 않으면 false를 반환한다.")
    void isValidOrderId_notExistOrder_falseReturned() {
        // given
        final Long customerId = 1L;
        final Long orderId = orderDao.addOrders(customerId);

        // when
        final boolean actual = orderDao.isValidOrderId(customerId, orderId + 10L);

        // then
        assertThat(actual).isFalse();
    }
}
