package cart.domain;

import static cart.factory.CartFactory.createCart;
import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemsTest {

    @Test
    @DisplayName("카트를 생성할 수 있다.")
    void create_cart_success() {
        //given
        final var cart = new CartItems(List.of());
        //when
        //then
        assertThat(cart).isNotNull()
                .isInstanceOf(CartItems.class);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void add_cart_item_success() {
        //given
        CartItems cartItems = createCart();
        //when
        CartItem newCartItem = new CartItem(createProduct());
        cartItems.add(newCartItem);
        //then
        assertThat(cartItems).extracting("cartItems", InstanceOfAssertFactories.LIST).contains(newCartItem);
    }
}