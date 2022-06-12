package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.invalid.InvalidQuantityException;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 55, 99})
    void 수량_생성(int value) {
        // when
        Quantity quantity = new Quantity(value);

        // then
        assertThat(quantity.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100, 150})
    void 수량이_1부터_99까지의_정수가_아닌_경우_예외_발생(int quantity) {
        assertThatThrownBy(() -> new Quantity(quantity))
                .isInstanceOf(InvalidQuantityException.class);
    }

}
