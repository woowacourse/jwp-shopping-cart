package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 1000000001})
    void 가격은_음수이거나_10억_초과일_수_없다(final Integer price) {
        Assertions.assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 10억 이하의 음이 아닌 정수여야 합니다.");
    }
}
