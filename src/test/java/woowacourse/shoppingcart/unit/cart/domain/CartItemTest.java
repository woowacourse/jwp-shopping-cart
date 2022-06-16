package woowacourse.shoppingcart.unit.cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.exception.badrequest.InvalidQuantityException;
import woowacourse.shoppingcart.product.domain.Product;

class CartItemTest {

    @Test
    @DisplayName("수량을 변경한다.")
    void changeQuantity() {
        // given
        final Product product = new Product(1L, "빠나나", 850, "bbanana.com");
        final CartItem cartItem = new CartItem(1L, product);

        final int expected = 7;

        // when
        final CartItem actual = cartItem.changeQuantity(expected);

        // then
        assertThat(actual.getQuantity()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 수량으로 변경하면 예외를 던진다.")
    @ValueSource(ints = {-1, 0})
    void changeQuantity_invalidValue_exceptionThrown(final int quantity) {
        // given
        final Product product = new Product(1L, "빠나나", 850, "bbanana.com");
        final CartItem cartItem = new CartItem(1L, product);

        // when, then
        assertThatThrownBy(() -> cartItem.changeQuantity(quantity))
                .isInstanceOf(InvalidQuantityException.class);
    }
}
