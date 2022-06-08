package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @ParameterizedTest(name = "수량이 0 이하이면 예외가 발생한다.: {0}")
    @ValueSource(ints = {0, -1})
    void name(int value) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(value))
                .withMessageContaining("1 이상");

    }
}
