package cart.domain;

import static cart.factory.ProductFactory.createOtherProduct;
import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {
    @Test
    @DisplayName("카트 아이템은 상품으로 생성할 수 있다.")
    void create_cart_item_success() {
        //given
        final var cartItem = new CartItem(createProduct());
        //when
        //then
        assertThat(cartItem)
                .isNotNull()
                .isInstanceOf(CartItem.class);
    }

    @Test
    @DisplayName("id가 같으면 같은 상품이다.")
    void equals() {
        //given
        CartItem cartItem = new CartItem(1L, createProduct());
        //when
        CartItem otherItem = new CartItem(1L, createOtherProduct());
        //then
        assertThat(cartItem).isEqualTo(otherItem);
    }
}