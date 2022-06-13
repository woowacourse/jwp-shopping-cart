package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.QuantityNotMatchException;

class CartTest {

    @DisplayName("장바구니에 저장되어 있는 수량과 일치하는지 검증한다.")
    @Test
    void checkQuantity() {
        final Cart cart = new Cart(1L, new Quantity(5), PRODUCT_BANANA);

        assertDoesNotThrow(() -> cart.checkQuantity(5));
    }

    @DisplayName("장바구니에 저장되어 있는 수량과 일치하지 않으면 예외가 발생한다.")
    @Test
    void checkQuantityException() {
        final Cart cart = new Cart(1L, new Quantity(5), PRODUCT_BANANA);

        assertThatThrownBy(() -> cart.checkQuantity(10))
                .isExactlyInstanceOf(QuantityNotMatchException.class)
                .hasMessageContaining("장바구니에 저장되어있는 상품의 수량과 주문하려는 수량이 다릅니다.");
    }
}
