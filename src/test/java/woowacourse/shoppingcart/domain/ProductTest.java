package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품의 재고를 감소한다.")
    @Test
    void removeStock() {
        Product product = new Product("치킨", 10_000, 10, "http://example.chicken.jpg");
        product.removeStock(5);

        assertThat(product.getStock()).isEqualTo(5);
    }

    @DisplayName("상품의 현재 재고보다 더 많이 꺼낼 수 없다.")
    @Test
    void removeOverStock() {
        Product product = new Product("치킨", 10_000, 10, "http://example.chicken.jpg");

        assertThatThrownBy(() -> product.removeStock(12));
    }
}
