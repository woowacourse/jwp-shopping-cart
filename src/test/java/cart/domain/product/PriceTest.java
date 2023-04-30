package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10_000_000})
    @DisplayName("상품 가격을 정상적으로 등록할 수 있다")
    void priceTest(final int input) {
        assertDoesNotThrow(() -> Price.from(input));
    }

    @DisplayName("범위를 넘어서는 가격이 들어오면 예외를 발생한다")
    @ValueSource(ints = {0, 10_000_001})
    @ParameterizedTest
    void throwExceptionWhenInvalidImage(final int input) {
        assertThatThrownBy(() -> Price.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
