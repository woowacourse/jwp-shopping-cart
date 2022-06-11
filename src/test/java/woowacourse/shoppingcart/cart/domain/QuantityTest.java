package woowacourse.shoppingcart.cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.cart.support.exception.CartException;
import woowacourse.shoppingcart.cart.support.exception.CartExceptionCode;

class QuantityTest {

    @DisplayName("수량은 양수여야 한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void validateQuantityPositive(final long value) {
        assertThatThrownBy(() -> new Quantity(value))
                .isInstanceOf(CartException.class)
                .extracting("exceptionCode")
                .isEqualTo(CartExceptionCode.INVALID_FORMAT_QUANTITY);
    }
}
