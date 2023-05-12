package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PriceTest {

    @DisplayName("잘못된 범위의 가격이 들어오면 예외처리하는지 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void throwExceptionWhenInvalidPriceTest(final int priceInput) {
        assertThatThrownBy(() -> new Price(priceInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상 범위의 가격이 들어오는 경우 확인한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 10_000_000})
    void successTest(final int priceInput) {
        assertDoesNotThrow(() -> new Price(priceInput));
    }

}
