package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidProductException;

class ProductTest {

    @Test
    @DisplayName("가격이 0보다 작게 저장될 경우 예외")
    void createMinussPrice_throwException() {
        assertThatThrownBy(() -> new Product(1L, "치킨", -1, 2, "url"))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("제품의 가격은 0보다 작을 수 없습니다.");
    }

    @Test
    @DisplayName("수량이 0보다 작게 저장될 경우 예외")
    void createMinusStock_throwException() {
        assertThatThrownBy(() -> new Product(1L, "치킨", 1000, -1, "url"))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("제품의 수량은 0보다 작을 수 없습니다.");
    }

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
