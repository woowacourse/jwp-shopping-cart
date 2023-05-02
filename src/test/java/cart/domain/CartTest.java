package cart.domain;

import static cart.factory.CartFactory.createCart;
import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    @DisplayName("카트를 생성할 수 있다.")
    void create_cart_success() {
        //given
        final var cart = new Cart(List.of());
        //when
        //then
        assertThat(cart).isNotNull()
                .isInstanceOf(Cart.class);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void add_cart_item_success() {
        //given
        Cart cart = createCart();
        //when
        CartItem newCartItem = new CartItem(createProduct());
        cart.add(newCartItem);
        //then
        assertThat(cart).extracting("cartItems", InstanceOfAssertFactories.LIST).contains(newCartItem);
    }
}