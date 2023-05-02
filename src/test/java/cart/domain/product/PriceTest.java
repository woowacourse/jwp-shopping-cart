package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.product.Price;
import org.junit.jupiter.api.Test;


@SuppressWarnings("NonAsciiCharacters")
class PriceTest {

    @Test
    void 이름은_null_일수없습니다() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다.");
    }
}
