package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @Test
    @DisplayName("상품 가격이 0원 미만이면 예외가 발생한다.")
    void validatePriceTest_belowMinPrice() {
        // given
        int price = -1;

        // then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상 500,000,000원 이하여야 합니다. (현재 " + price + "원)");
    }

    @Test
    @DisplayName("상품 가격이 5억원 초과이면 예외가 발생한다.")
    void validatePriceTest_aboveMaxPrice() {
        // given
        int price = 500_000_001;

        // then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상 500,000,000원 이하여야 합니다. (현재 " + price + "원)");
    }
}