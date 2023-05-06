package cart.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 100, 5000})
    @DisplayName("Quantity를 생성할 수 있다.")
    void create(int value) {
        final Quantity quantity = new Quantity(value);
        assertThat(quantity.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10, -100, -5000})
    @DisplayName("Quantity에 음수가 들어오면 예외가 발생한다.")
    void validateNegative(int value) {
        assertThatThrownBy(() -> new Quantity(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
