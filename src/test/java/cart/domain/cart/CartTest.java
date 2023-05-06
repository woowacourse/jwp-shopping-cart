package cart.domain.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    @DisplayName("productId, memberId로 Cart를 생성할 수 있다.")
    void createByTwoParameters() {
        // given
        final Long productId = 1L;
        final Long memberId = 1L;
        // when
        final Cart cart = new Cart(productId, memberId);
        // then
        assertEquals(cart.getProductId(), productId);
        assertEquals(cart.getMemberId(), memberId);
    }

    @Test
    @DisplayName("productId, memberId, quantity로 Cart를 생성할 수 있다.")
    void createByThreeParameters() {
        // given
        final Long productId = 1L;
        final Long memberId = 2L;
        final Quantity quantity = new Quantity(2);
        // when
        final Cart cart = new Cart(productId, memberId, quantity);
        // then
        assertEquals(cart.getProductId(), productId);
        assertEquals(cart.getMemberId(), memberId);
        assertEquals(cart.getQuantity(), quantity);
    }
}
