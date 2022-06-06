package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("상품 금액이 0 미만이면 예외를 던진다.")
    @Test
    void checkBoundary() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 금액은 0원 이상이어야 합니다.");
    }
}
