package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidCartQuantityException;
import woowacourse.shoppingcart.exception.InvalidProductPriceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {

    @DisplayName("가격이 음수일 경우 예외가 발생한다.")
    @Test
    void validatePrice() {
        int price = -1;
        assertThatThrownBy(() -> new Cart(1L, 1L, "콜라", price, "image", 1))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("수량이 음수일 경우 예외가 발생한다.")
    @Test
    void validateQuantity() {
        int quantity = -1;
        assertThatThrownBy(() -> new Cart(1L, 1L, "콜라", 100, "image", quantity))
                .isInstanceOf(InvalidCartQuantityException.class);
    }

    @DisplayName("총 금액을 올바르게 계산한다.")
    @Test
    void calculateTotalPrice() {
        int quantity = 10;
        int price = 1_600;
        Cart cart = new Cart(1L, 1L, "콜라", price, "image", quantity);
        int totalPrice = cart.getTotalPrice();

        assertThat(totalPrice).isEqualTo(16_000);
    }
}
