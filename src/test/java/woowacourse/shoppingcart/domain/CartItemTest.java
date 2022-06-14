package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @DisplayName("수량을 수정한다.")
    @Test
    void updateQuantity() {
        // given
        Product product = new Product("치킨", 10_000, 10, "http://example.com/chicken.jpg");
        CartItem cartItem = new CartItem(1L, product);

        // when
        cartItem.updateQuantity(5);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(5);
    }

    @DisplayName("수량은 음수로 수정할 수 없다.")
    @Test
    void updateInvalidQuantity() {
        // given
        Product product = new Product("치킨", 10_000, 10, "http://example.com/chicken.jpg");

        // when
        CartItem cartItem = new CartItem(1L, product);

        // then
        assertThatThrownBy(() -> cartItem.updateQuantity(-1));
    }
}