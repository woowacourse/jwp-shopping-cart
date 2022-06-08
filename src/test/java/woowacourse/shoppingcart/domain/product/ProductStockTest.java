package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;
import woowacourse.shoppingcart.exception.OutOfStockException;

public class ProductStockTest {

    @DisplayName("재고 차감 기능 확인")
    @Test
    void reduce() {
        Product product = new Product(1L, "워치", 1_000, new ThumbnailImage("url", "alt"));
        ProductStock productStock = new ProductStock(product, new Quantity(10));
        CartItem cartItem = new CartItem(product, new Quantity(8));

        ProductStock reducedProductStock = productStock.reduce(cartItem);

        assertThat(reducedProductStock.getStockQuantity()).isEqualTo(2);
    }

    @DisplayName("재고보다 주문 수량이 많을 시 에러 반환")
    @Test
    void cantOrder() {
        Product product = new Product(1L, "워치", 1_000, new ThumbnailImage("url", "alt"));
        ProductStock productStock = new ProductStock(product, new Quantity(10));
        CartItem cartItem = new CartItem(product, new Quantity(12));

        assertThatThrownBy(() -> productStock.reduce(cartItem)).isInstanceOf(OutOfStockException.class);
    }
}
