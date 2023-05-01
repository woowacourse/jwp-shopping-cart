package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격이 0 이하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1 ,0})
    void exceptionWhenWrongPrice(int price) {
        // when, then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가격은 0보다 커야합니다.");
    }

}