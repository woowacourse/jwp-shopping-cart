package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PriceTest {

    @Test
    @DisplayName("가격을 정상적으로 생성한다.")
    void create_price_success() {
        // given
        int price = 1000;

        // when & then
        assertDoesNotThrow(() -> new Price(price));
    }

    @Test
    @DisplayName("가격이 0원 미만인 경우 예외를 발생한다.")
    void throws_exception_when_price_is_less_than_minimum() {
        // given
        int price = -100;

        // when & then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
