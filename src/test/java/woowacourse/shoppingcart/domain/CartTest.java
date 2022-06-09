package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    @DisplayName("현재 카트에 해당 상품이 들어있는지 확인한다.")
    @Test
    void containsProductId() {
        // given
        CartItem cartItem = new CartItem(0L, 1L, 1L, 30);
        CartItem cartItem2 = new CartItem(1L, 1L, 2L, 30);
        Cart cart = new Cart(List.of(cartItem, cartItem2));

        // when then
        assertThat(cart.containsProductId(1L)).isTrue();
    }
}
