package woowacourse.shoppingcart.domain.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PriceTest {

    @ParameterizedTest(name = "가격 : {0}")
    @ValueSource(ints = {1_000, 1})
    void 가격_생성(int value) {
        Price price = new Price(value);
        assertThat(price).isNotNull();
    }

    @ParameterizedTest(name = "가격 : {0}")
    @ValueSource(ints = {0, -1})
    void 최소_금액보다_낮은_가격_생성(int value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(value))
                .withMessageContaining("가격은 1원 이상이어야 합니다.");
    }

    @Test
    void 가격에_숫자를_곱한_결과() {
        Price price = new Price(1_000);
        assertThat(price.multiple(5)).isEqualTo(5_000);
    }
}
