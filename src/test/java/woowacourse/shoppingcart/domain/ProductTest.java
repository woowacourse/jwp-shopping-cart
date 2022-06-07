package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 입고한다.")
    @Test
    void receive() {
        Product product = new Product("상품", 1_000, 0, "abc@woowa.com");

        product.receive(10);

        assertThat(product.getStock()).isEqualTo(10);
    }

    @DisplayName("상품을 출고한다.")
    @Test
    void release() {
        Product product = new Product("상품", 1_000, 1, "abc@woowa.com");

        product.release(1);

        assertThat(product.getStock()).isEqualTo(0);
    }

    @DisplayName("입/출고 하려는 수량이 0보다 작거나 같으면 예외를 반환한다.")
    @Test
    void quantityCannotLessThanOrEqualTo0() {
        Product product = new Product("상품", 1_000, 1, "abc@woowa.com");

        assertThatThrownBy(() -> product.release(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 수량은 0보다 커야 합니다.");
    }

    @DisplayName("재고보다 많은 수량을 출고하려고 하면 예외를 반환한다.")
    @Test
    void cannotRelease() {
        Product product = new Product("상품", 1_000, 1, "abc@woowa.com");

        assertThatThrownBy(() -> product.release(2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 충분하지 않습니다.");
    }
}
