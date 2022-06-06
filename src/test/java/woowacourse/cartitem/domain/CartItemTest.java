package woowacourse.cartitem.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

public class CartItemTest {

    @DisplayName("카트 아이템을 생성한다.")
    @Test
    void createCartItem() {
        final Product product = new Product(1L, "product", new Price(10), new Stock(1), "test");
        assertDoesNotThrow(() -> new CartItem(1L, product, new Quantity(1)));
    }
}
