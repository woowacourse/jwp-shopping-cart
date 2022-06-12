package woowacourse.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.product.exception.InvalidProductException;

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

    @DisplayName("상품의 재고보다 많은 수량을 담으려는 경우 예외를 반환한다.")
    @Test
    void checkAvailableForPurchase() {
        final Product product = new Product(name, new Price(price), new Stock(stock), imageURL);

        assertThatThrownBy(() -> product.checkProductAvailableForPurchase(2))
            .isInstanceOf(InvalidProductException.class)
            .hasMessage("남아있는 재고보다 많은 수량을 카트에 담을 수 없습니다.");
    }
}
