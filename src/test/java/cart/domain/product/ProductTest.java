package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
    private static final String PRODUCT_NAME = "글렌피딕";
    private static final int PRICE = 10000;
    private static final String IMAGE_URL = "https://image.com/image.png";

    @Test
    @DisplayName("상품이 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Product product = new Product(new ProductName(PRODUCT_NAME), new Price(PRICE), ImageUrl.from(IMAGE_URL));

        // expect
        assertThat(product.getName())
                .isEqualTo("글렌피딕");
        assertThat(product.getPrice())
                .isEqualTo(10000);
        assertThat(product.getImageUrl())
                .isEqualTo("https://image.com/image.png");
    }
}
