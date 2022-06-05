package woowacourse.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private final String name = "짱구";
    private final int price = 100_000_000;
    private final int stock = 1;
    private final String imageURL = "http://example.com/jjanggu.jpg";

    @DisplayName("상품을 생성한다.")
    @Test
    void createProduct() {
        final Product product = new Product(name, new Price(price), new Stock(stock), imageURL);

        assertAll(
            () -> assertThat(product.getName()).isEqualTo(name),
            () -> assertThat(product.getPrice().getValue()).isEqualTo(price),
            () -> assertThat(product.getStock().getValue()).isEqualTo(stock),
            () -> assertThat(product.getImageURL()).isEqualTo(imageURL)
        );
    }
}
