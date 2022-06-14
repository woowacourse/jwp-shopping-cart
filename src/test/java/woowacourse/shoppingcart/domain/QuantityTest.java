package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.exception.bodyexception.ValidateException;

public class QuantityTest {

    @DisplayName("수량이 양수가 아닌 수가 들어오는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void new_notPositive_exceptionThrown(int value) {
        // when, then
        assertThatThrownBy(() -> new Quantity(value))
                .isInstanceOf(ValidateException.class)
                .hasMessage("잘못된 형식입니다.");
    }
}
