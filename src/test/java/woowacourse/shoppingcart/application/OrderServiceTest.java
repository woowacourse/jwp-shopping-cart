package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.dto.OrderServiceRequest;
import woowacourse.shoppingcart.exception.CartNotFoundException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.ui.dto.OrdersResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrderServiceTest {

    private final OrderService orderService;

    public OrderServiceTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @DisplayName("올바른 데이터로 주문을 하면 주문 ID를 반환한다.")
    @Test
    void addOrder() {
        long memberId = 2L;
        List<OrderServiceRequest> orderRequests = List.of(new OrderServiceRequest(3L));

        long id = orderService.addOrder(orderRequests, memberId);
        assertThat(id).isNotNull();
    }

    @DisplayName("잘못된 장바구니 정보를 포함한채 주문을 하면 예외가 발생한다.")
    @Test
    void addOrderWithWrongProductId() {
        long memberId = 2L;
        List<OrderServiceRequest> orderRequests = List.of(new OrderServiceRequest(7L));

        assertThatThrownBy(() -> orderService.addOrder(orderRequests, memberId))
                .isInstanceOf(CartNotFoundException.class)
                .hasMessageContaining("존재하지 않는 장바구니 정보입니다.");
    }

    @DisplayName("단일 주문 정보를 조회한다.")
    @Test
    void findOrder() {
        long memberId = 1L;
        long orderId = 1L;
        OrdersResponse ordersResponse = orderService.findOrder(memberId, orderId);

        assertAll(
                () -> assertThat(ordersResponse.getId()).isEqualTo(orderId),
                () -> assertThat(ordersResponse.getOrderDetailResponses().size()).isEqualTo(2)
        );
    }

    @DisplayName("잘못된 주문 번호로 단일 주문 정보를 조회시 예외가 발생한다.")
    @Test
    void findOrderWithWrongOrderId() {
        long memberId = 1L;
        long orderId = 2L;
        assertThatThrownBy(() -> orderService.findOrder(memberId, orderId))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessageContaining("잘못된 주문 정보입니다.");
    }

    @DisplayName("회원의 모든 주문 정보를 조회한다.")
    @Test
    void findOrders() {
        long memberId = 4L;

        List<OrdersResponse> orders = orderService.findOrders(memberId);
        assertThat(orders.size()).isEqualTo(2);
    }
}
