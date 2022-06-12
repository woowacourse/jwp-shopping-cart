package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.ui.dto.OrderDetailResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersResponseDetailDaoTest {

    private final OrderDetailDao orderDetailDao;

    public OrdersResponseDetailDaoTest(JdbcTemplate jdbcTemplate) {
        this.orderDetailDao = new OrderDetailDao(jdbcTemplate);
    }

    @DisplayName("OrderDatail를 저장하면, ID를 반환한다.")
    @Test
    void save() {
        long orderId = 1L;
        long productId = 2L;
        int quantity = 5;

        long orderDetailId = orderDetailDao.save(orderId, productId, quantity);

        assertThat(orderDetailId).isEqualTo(5L);
    }

    @DisplayName("OrderId를 통해 매칭되는 주문 목록들을 조회한다.")
    @Test
    void findOrdersDetailsByOrderId() {
        long orderId = 1L;
        List<OrderDetailResponse> orderDetails = orderDetailDao.findOrdersDetailsByOrderId(orderId);

        assertThat(orderDetails.size()).isEqualTo(2);
    }
}
