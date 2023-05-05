package cart.service.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductPriceTest {

    @Test
    void 상품_금액이_10억_초과면_예외발생() {
        Assertions.assertThatThrownBy(() -> new ProductPrice(1_000_000_001))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 가격은 최대 10억입니다.");
    }

}
