package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ThumbnailImage;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;

public class CartItemTest {

    @DisplayName("카트에 담으려는 제품이 같으면 에러를 반환한다.")
    @Test
    void checkSameProduct() {
        Product product = new Product(1L, "애플워치", 10_000, new ThumbnailImage("url", "alt"));

        CartItem cartItem1 = new CartItem(product, 10);
        CartItem cartItem2 = new CartItem(product, 20);

        assertThatThrownBy(() -> cartItem1.checkSameProduct(cartItem2)).isInstanceOf(
            DuplicateProductInCartException.class);
    }
}
