package woowacourse.shoppingcart.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidFormException;

class QuantityTest {

    @Test
    @DisplayName("수량이 음수인 경우, 예외를 발생한다.")
    void negativeQuantityException() {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new Quantity(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("수량이 양수인 경우, 수량을 생성한다.")
    void createQuantity(int value) {
        Quantity quantity = new Quantity(value);
        assertThat(quantity.getValue()).isEqualTo(value);
    }
}
