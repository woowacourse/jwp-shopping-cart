package cart.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PriceTest {
    @ParameterizedTest(name = "{displayName} : price = {0}")
    @ValueSource(ints = {0, 10_000_000})
    void 가격_정상_입력(int price) {
        assertThatNoException()
                .isThrownBy(() -> new Price(price));
    }
    
    @ParameterizedTest(name = "{displayName} : price = {0}")
    @ValueSource(ints = {-1, 10_000_001})
    void 가격_범위_초과_시_예외_처리(int price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(price))
                .withMessage("[ERROR] 입력할 수 있는 가격의 범위를 벗어났습니다.");
    }
    
    @Test
    void 가격이_null일_시_예외_처리() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(null))
                .withMessage("[ERROR] 상품 가격을 입력해주세요.");
    }
}
