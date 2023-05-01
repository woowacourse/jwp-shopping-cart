package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("상품 가격은")
class PriceTest {

    @DisplayName("1000원 ~ 100만원 사이가 아니면 예외처리 된다.")
    @ParameterizedTest(name = "케이스: {0}")
    @ValueSource(ints = {999, 1_000_001})
    void validatePriceRangeTest(int input) {
        assertThatThrownBy(() -> new Price(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 가격은 1000원 이상, 100만원 이하만 가능합니다.");
    }
}
