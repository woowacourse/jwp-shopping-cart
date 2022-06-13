package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidOrderException;

public class OrderDetailTest {

    @DisplayName("주문 물품 개수가 1개 미만이면 에러를 발생한다.")
    @Test
    void validateOrderDetailQuantity() {
        assertThatThrownBy(() -> new OrderDetail(1L, 1L, 0))
                .isInstanceOf(InvalidOrderException.class);
    }

}
