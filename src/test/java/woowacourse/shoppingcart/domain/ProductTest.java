package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidProductException;

class ProductTest {

    @Test
    @DisplayName("제품의 수량보다 더 주문할 경우 예외")
    void purchaseProductLargeQuantity_throwException() {
        Product product = new Product("치킨", 1000, 5, "url");

        assertThatThrownBy(() -> product.purchaseProduct(6))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("제품의 수량보다 더 주문할 수 없습니다.");
    }

    @Test
    @DisplayName("수량 구매")
    void purchaseProduct() {
        Product product = new Product("치킨", 1000, 5, "url")
                .purchaseProduct(5);
        assertThat(product.getStock()).isEqualTo(0);
    }
}
