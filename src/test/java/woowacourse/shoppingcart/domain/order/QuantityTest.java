package woowacourse.shoppingcart.domain.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    @DisplayName("99개를 넘으면 예외를 반환한다.")
    void invalidQuantityLimit() {
        //given

        //when

        //then
        assertThatThrownBy(
                () -> new Quantity(100)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 수는 99개를 초과할 수 없습니다.");
    }
}
