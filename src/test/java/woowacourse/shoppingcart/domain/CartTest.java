package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.IllegalCartItemException;

class CartTest {

    @DisplayName("장바구니에 이미 담긴 상품을 추가하면 예외가 발생한다.")
    @Test
    void addItem_already_added() {
        Product product1 = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem1 = new CartItem(product1, 1);
        Cart cart = new Cart(List.of(cartItem1));

        Product product2 = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem2 = new CartItem(product2, 3);

        assertThatExceptionOfType(IllegalCartItemException.class)
                .isThrownBy(() -> cart.addItem(cartItem2))
                .withMessageContaining("이미 담긴");
    }
}
