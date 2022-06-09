package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @DisplayName("구매 수량을 받아, 자신의 재고가 더 많거나 같은지 반환한다.")
    @ParameterizedTest
    @CsvSource({"100, false", "101, true"})
    void hasLowerStock(int purchasingQuantity, boolean expected) {
        Product product = new Product(1L, "상품이름", 1000, 100, "url");

        boolean actual = product.hasLowerStock(purchasingQuantity);

        assertThat(actual).isEqualTo(expected);
    }
}