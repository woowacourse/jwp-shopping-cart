package cart.domain.product;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10_000_000})
    void 상품_가격을_정상적으로_등록할_수_있다(final int input) {
        assertDoesNotThrow(() -> Price.from(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10_000_001})
    void 범위를_넘어서는_가격이_들어오면_예외를_발생한다(final int input) {
        assertThatThrownBy(() -> Price.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
