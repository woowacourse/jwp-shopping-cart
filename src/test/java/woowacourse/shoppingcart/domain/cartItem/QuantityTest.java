package woowacourse.shoppingcart.domain.cartItem;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.format.InvalidQuantityException;

class QuantityTest {
    @DisplayName("수량 객체를 생성한다,")
    @Test
    public void construct() {
        // given
        int quantity = 3;

        // when & then
        assertThatCode(() -> new Quantity(quantity)).doesNotThrowAnyException();
    }

    @DisplayName("수량이 음수일 경우 예외가 발생한다.")
    @Test
    public void constructByInvalidQuantity() {
        // given
        int quantity = -1;

        // when & then
        assertThatThrownBy(() -> new Quantity(quantity)).isInstanceOf(InvalidQuantityException.class);
    }
}