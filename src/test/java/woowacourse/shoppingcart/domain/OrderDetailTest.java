package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderDetailTest {

    private Product rice;

    @Test
    @DisplayName("장바구니 구매 항목의 총 금액을 구한다.")
    void calculateTotalPrice() {
        // given
        rice = new Product("rice", 1000, "www.naver.com");

        // when
        final OrderDetail orderDetail = new OrderDetail(rice, 2);

        // then
        assertThat(orderDetail.calculateTotalPrice()).isEqualTo(2_000);
    }
}
