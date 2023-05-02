package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductPriceTest {

    private static final long MINIMUM_AMOUNT = 0;

    @DisplayName("상품 가격이 0원 미만이면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenPriceIsLowerThanZero() {
        assertThatThrownBy(() -> new ProductPrice(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품 가격은 %d원 이상이어야 합니다.", MINIMUM_AMOUNT);
    }

}
