package woowacourse.shoppingcart.domain.product.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {
    @DisplayName("음수의 가격은 존재할 수 없기에 예외 발생")
    @Test
    void validateNegative() {
        assertThatThrownBy(() -> new Price(-100)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("금액은 음수일 수 없습니다.");
    }
}
