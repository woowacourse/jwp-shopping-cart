package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductPriceTest {

    @Test
    @DisplayName("가격이 음수일 경우 예외 발생")
    void priceNegative() {
        assertThatThrownBy(() -> new ProductPrice(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductPrice.PRICE_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("가격이 10_000_000 초과일 경우 예외 발생")
    void priceMoreThanMax() {
        assertThatThrownBy(() -> new ProductPrice(10_000_001))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductPrice.PRICE_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("가격이 정상일 경우")
    @ValueSource(ints = {0, 10_000_000})
    void priceSuccess(final int price) {
        assertDoesNotThrow(() -> new ProductPrice(price));
    }
}
