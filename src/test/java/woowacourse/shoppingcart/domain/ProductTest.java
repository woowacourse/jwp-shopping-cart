package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품의 재고를 감소한다.")
    @Test
    void removeQuantity() {
        Product product = new Product("치킨", 10_000, 10, "http://example.chicken.jpg");
        product.removeQuantity(5);

        assertThat(product.getQuantity()).isEqualTo(5);
    }

    @DisplayName("상품의 현재 재고보다 더 많이 꺼낼 수 없다.")
    @Test
    void removeOverQuantity() {
        Product product = new Product("치킨", 10_000, 10, "http://example.chicken.jpg");

        assertThatThrownBy(() -> product.removeQuantity(12))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 재고를 증가한다.")
    @Test
    void addQuantity() {
        Product product = new Product("치킨", 10_000, 10, "http://example.chicken.jpg");
        product.addQuantity(5);

        assertThat(product.getQuantity()).isEqualTo(15);
    }
}
