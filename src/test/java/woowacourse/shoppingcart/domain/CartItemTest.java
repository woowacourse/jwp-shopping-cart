package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartItemTest {

    @Test
    @DisplayName("상품에 null이 들어온 경우")
    void validateProductNull() {
        assertThatThrownBy(() -> new CartItem(1L, 1L, null, 1L, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품과 수량에는 null이 들어올 수 없습니다.");
    }

    @Test
    @DisplayName("수량에 null이 들어온 경우")
    void validateQuantityNull() {
        assertThatThrownBy(() -> new CartItem(1L, 1L, new Product("피자", 20000, "http://example.com/chicken.jpg"),
                null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품과 수량에는 null이 들어올 수 없습니다.");
    }

    @Test
    @DisplayName("수량에 음수가 들어온 경우")
    void validateQuantityNegative() {
        assertThatThrownBy(() -> new CartItem(1L, 1L, new Product("피자", 20000, "http://example.com/chicken.jpg"),
                -1L, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("수량에는 음수가 들어올 수 없습니다.");
    }
}
