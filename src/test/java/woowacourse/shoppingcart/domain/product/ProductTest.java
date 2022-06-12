package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 생성한다.")
    @Test
    void createProduct() {
        // given & when
        String name = "productName";
        int price = 1000;
        String imageUrl = "http://example.com/image.png";
        final String description = "this is sample-description";

        // then
        assertThatCode(() -> new Product(name, price, imageUrl, description))
            .doesNotThrowAnyException();
    }
}