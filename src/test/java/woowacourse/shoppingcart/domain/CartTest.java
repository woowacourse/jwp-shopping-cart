package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.helper.fixture.CartFixture.getFirstCart;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartTest {

    @DisplayName("물품 개수가 1개 미만일 경우 예외를 발생한다.")
    @Test
    void validateQuantity() {
        assertThatThrownBy(() -> new Cart(1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 0))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("총 비용을 계산한다.")
    @Test
    void calculateTotalPrice() {
        final Cart cart = new Cart(1L, 1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 10);
        assertThat(cart.calculateTotalPrice()).isEqualTo(10_000);
    }

    @DisplayName("카트 내 품목의 구매 개수를 변경한다.")
    @Test
    void updateQuantity() {
        final Cart cart = new Cart(1L, 1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 10);
        cart.updateQuantity(1);
        assertThat(cart.getQuantity()).isEqualTo(1);
    }

    @DisplayName("카트 내 품목의 구매 개수를 올바르게 변경하지 않으면 예외가 발생한다..")
    @Test
    void updateQuantityException() {
        final Cart cart = new Cart(1L, 1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 10);
        assertThatThrownBy(() -> cart.updateQuantity(0))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("카트의 물품과 동일한 상품이다.")
    @Test
    void isSameProduct() {
        final Cart cart = getFirstCart();
        assertThat(cart.isSameProduct(new Product(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE))).isTrue();
    }

    @DisplayName("개수를 한개 늘린다.")
    @Test
    void addQuantity() {
        final Cart cart = getFirstCart();
        cart.addQuantity();
        assertThat(cart.getQuantity()).isEqualTo(2);
    }

}
