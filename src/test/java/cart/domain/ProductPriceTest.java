package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductPriceTest {

    @Test
    void 이름은_null_일수없습니다() {
        assertThatThrownBy(() -> new ProductPrice(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다.");
    }
}
