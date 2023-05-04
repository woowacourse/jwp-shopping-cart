package cart.domain.cart;

import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cart.factory.cart.CartItemsFactory.createCartItems;
import static cart.factory.product.ProductFactory.createOtherProduct;
import static cart.factory.product.ProductFactory.createProduct;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CartItemsTest {

    @Test
    @DisplayName("품목을 장바구니에 추가한다.")
    void add_product_success() {
        // given
        Product product = createProduct();
        CartItems cartItems = createCartItems(product);

        // when
        cartItems.add(product);

        // then
        assertThat(cartItems.contains(product)).isTrue();
    }

    @Test
    @DisplayName("품목을 장바구니에서 제거한다.")
    void delete_product_success() {
        // given
        Product product = createProduct();
        CartItems cartItems = createCartItems(product);

        // when
        cartItems.remove(product);

        // then
        assertThat(cartItems.contains(product)).isFalse();
    }

    @Test
    @DisplayName("품목이 포함되어 있는지 확인한다.")
    void contains_product_success() {
        // given
        Product product = createProduct();
        CartItems cartItems = createCartItems(product);

        // when
        boolean result = cartItems.contains(product);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마지막 품목을 반환한다.")
    void returns_last_cart_item() {
        // given
        Product product = createProduct();
        Product otherProduct = createOtherProduct();
        CartItems cartItems = createCartItems(product, otherProduct);

        // when
        Product lastCartItem = cartItems.getLastCartItem();

        // then
        assertThat(lastCartItem.equals(otherProduct)).isTrue();
    }
}
