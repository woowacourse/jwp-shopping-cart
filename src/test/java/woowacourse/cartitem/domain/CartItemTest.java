package woowacourse.cartitem.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartItemTest {

    @DisplayName("카트 아이템을 생성한다.")
    @Test
    void createCartItem() {
        assertDoesNotThrow(() -> new CartItem(1L, 1L, new Quantity(1)));
    }
}
