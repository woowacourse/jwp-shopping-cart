package cart.domain;

import static cart.factory.ProductFactory.createOtherProduct;
import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartProductTest {
    @Test
    @DisplayName("카트 아이템은 상품으로 생성할 수 있다.")
    void create_cart_item_success() {
        //given
        final var cartItem = new CartProduct(createProduct());
        //when
        //then
        assertThat(cartItem)
                .isNotNull()
                .isInstanceOf(CartProduct.class);
    }

    @Test
    @DisplayName("id가 같으면 같은 상품이다.")
    void equals() {
        //given
        CartProduct cartProduct = new CartProduct(1L, createProduct());
        //when
        CartProduct otherItem = new CartProduct(1L, createOtherProduct());
        //then
        assertThat(cartProduct).isEqualTo(otherItem);
    }
}