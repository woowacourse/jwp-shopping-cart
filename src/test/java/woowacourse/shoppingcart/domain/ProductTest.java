package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidProductPriceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @ParameterizedTest(name = "가격은 양의 정수여야 한다. - 가격이 {0}일 경우 예외가 발생한다.")
    @ValueSource(ints = {-1, 0})
    void priceWithNegativeInteger(int price) {
        assertThatThrownBy(() -> new Product("초콜릿", price, "image.jpg"))
                .isInstanceOf(InvalidProductPriceException.class)
                .hasMessageContaining("상품 가격은 양의 정수가 되어야 합니다.");
    }

    @DisplayName("내부 필드값이 같을 경우 동등하다 판단한다.")
    @Test
    void equals() {
        Product product1 = new Product("초콜릿", 1_000, "image.jpg");
        Product product2 = new Product("초콜릿", 1_000, "image.jpg");

        assertThat(product1).isEqualTo(product2);
    }
}
