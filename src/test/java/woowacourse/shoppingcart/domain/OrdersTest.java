package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

class OrdersTest {

    @DisplayName("주문 상품들의 전체 가격을 계산할 수 있다.")
    @Test
    void totalPrice() {
        // given
        Long id = 1L;
        final List<OrderDetail> orderDetails = List.of(new OrderDetail(1L, 100000, "닌텐도", "url", 20),
                new OrderDetail(2L, 4000000, "맥북프로m116안치", "url", 10));

        //when
        final Orders orders = new Orders(1L, orderDetails);
        final OrdersResponse ordersResponse = orders.toOrdersResponse();
        int actualTotalPrice = ordersResponse.getTotalPrice();
        int expectedTotalPrice = 42000000;

        //then
        assertThat(actualTotalPrice).isEqualTo(expectedTotalPrice);
    }

}
