package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartItemTest {

    private Product product = Product.builder()
            .productName("beer")
            .price(3_000)
            .stock(10)
            .imageUrl("beer.png")
            .build();

    @DisplayName("담는 수량이 0, 또는 음수일 때 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void cartItemQuantity_underZero_throwsException(int quantity) {
        assertThatThrownBy(() -> new CartItem(product, quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 최소 1개여야 합니다.");
    }
    
    @DisplayName("담는 수량이 상품 재고보다 많을 때 예외 발생 ")
    @Test
    void cartItemQuantity_overProductStock_throwsException() {
        assertThatThrownBy(() -> new CartItem(product, 11))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("해당 상품은 10개 남아있습니다");
    }
}
