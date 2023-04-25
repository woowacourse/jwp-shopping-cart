package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @ParameterizedTest(name = "상품 가격 생성 테스트")
    @ValueSource(ints = {100, 500, 1500})
    void createProductPrice(int input) {
        assertDoesNotThrow(() -> ProductPrice.from(input));
    }

    @ParameterizedTest(name = "상품 가격은 100단위로 나뉘어 져야 한다.")
    @ValueSource(ints = {201, 520, 1515})
    void createProductPriceFailureNotUnit(int input) {
        assertThatThrownBy(() -> ProductPrice.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "상품 가격은 100보다 크거나 같아야 한다.")
    @ValueSource(ints = {99, 0, -1})
    void createProductPriceFailureUnderMinimumPrice(int input) {
        assertThatThrownBy(() -> ProductPrice.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
