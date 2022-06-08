package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrdersTest {

    @Test
    @DisplayName("해당 주문의 모든 항목의 결제 금액의 합계를 구한다.")
    void calculateTotalPrice() {
        // given
        final Product rice = new Product("rice", 1000, "www.naver.com");
        final Product bread = new Product("bread", 2000, "www.naver.com");
        final Product noodle = new Product("noodle", 3000, "www.naver.com");

        List<OrderDetail> orderDetails = List.of(
                new OrderDetail(rice, 1),
                new OrderDetail(bread, 2),
                new OrderDetail(noodle, 3)
        );

        // when
        final Orders orders = new Orders(1L, orderDetails);

        // then
        assertThat(orders.calculateTotalPrice()).isEqualTo(14_000L);
    }
}
