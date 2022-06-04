package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @DisplayName("상품명을 생성한다.")
    @Test
    void createProductName() {
        // given & when
        String value = "test-product";

        // then
        assertThatCode(() -> new ProductName(value))
            .doesNotThrowAnyException();
    }
}