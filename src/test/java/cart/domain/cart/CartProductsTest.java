package cart.domain.cart;

import static cart.factory.CartProductsFactory.createCart;
import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.CartProduct;
import cart.domain.cart.CartProducts;
import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartProductsTest {

    @Test
    @DisplayName("카트를 생성할 수 있다.")
    void create_cart_success() {
        //given
        final var cart = new CartProducts(List.of());
        //when
        //then
        assertThat(cart).isNotNull()
                .isInstanceOf(CartProducts.class);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void add_cart_item_success() {
        //given
        CartProducts cartProducts = createCart();
        //when
        CartProduct newCartProduct = new CartProduct(createProduct());
        cartProducts.add(newCartProduct);
        //then
        assertThat(cartProducts).extracting("cartItems", InstanceOfAssertFactories.LIST).contains(newCartProduct);
    }
}