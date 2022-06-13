package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@SpringBootTest
@Sql("/initSchema.sql")
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문을 추가한다.")
    void addOrder() {
        Long orderId = orderService.addOrder(1L, new OrderRequest(1L, 2));

        assertThat(orderId).isEqualTo(2L);
    }

    @Test
    @DisplayName("주문을 단건 조회한다.")
    void findOrderById() {
        OrderResponse orderResponse = orderService.findOrderById(1L, 1L);

        assertThat(orderResponse)
                .extracting("id", "productId", "thumbnail", "name", "price", "quantity")
                .containsExactly(1L, 1L, "woowa1.com", "banana", 1000, 3);
    }

    @Test
    @DisplayName("CustomerId를 이용하여 주문을 전체 조회한다.")
    void findOrdersByCustomerId() {
        orderService.addOrder(1L, new OrderRequest(1L, 5));

        List<OrderResponse> orders = orderService.findOrdersByCustomerId(1L);

        assertThat(orders).extracting("id", "quantity")
                .containsExactly(Tuple.tuple(1L, 3), Tuple.tuple(2L, 5));
    }
}