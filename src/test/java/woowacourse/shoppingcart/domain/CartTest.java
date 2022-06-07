package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        assertThatThrownBy(() -> new Cart(1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_IMAGE), 0))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @DisplayName("총 비용을 계산한다.")
    @Test
    void calculateTotalPrice() {
        final Cart cart = new Cart(1L, 1L, createProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 10);
        assertThat(cart.calculateTotalPrice()).isEqualTo(10_000);
    }

}
