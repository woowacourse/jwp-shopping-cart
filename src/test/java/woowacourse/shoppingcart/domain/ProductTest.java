package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("구매 가능한 충분한 재고가 있는지 확인한다.")
    @Test
    void isAvailable() {
        // given
        Product product = new Product(1L, "바삭한 감자칩", 3000, "imageUrl", 50);
        // when then
        assertThat(product.isAvailable(new Amount(30))).isTrue();
    }
}
