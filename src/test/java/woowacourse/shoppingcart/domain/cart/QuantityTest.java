package woowacourse.shoppingcart.domain.cart;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.attribute.NegativeNumberException;

class QuantityTest {

    @DisplayName("수량은 음수일 수 없다.")
    @Test
    void throwsExceptionWithNegativeNumber() {
        // given & when
        int amount = -1;
        // then
        assertThatExceptionOfType(NegativeNumberException.class)
            .isThrownBy(() -> new Quantity(amount));
    }

}