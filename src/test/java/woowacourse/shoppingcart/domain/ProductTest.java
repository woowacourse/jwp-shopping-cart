package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.product.Product;

public class ProductTest {

    @DisplayName("이름이 비었다면 예외 발생")
    @Test
    void blankProductName_throwException() {
        assertThatThrownBy(() -> new Product(" ", 1_000, 100, "product.png"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름은 비워둘 수 없습니다.");
    }

    @DisplayName("금액이 0원 또는 이하라면 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {0, -100})
    void zeroOrNegativePrice_throwException(int price) {
        assertThatThrownBy(() -> new Product("coffee", price, 100, "coffee.png"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 최소 1원이어야 합니다.");
    }

    @DisplayName("재고가 음수라면 예외 발생")
    @Test
    void stockUnderZero_throwException() {
        assertThatThrownBy(() -> new Product("coffee", 3_000, -1, "coffee.png"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고는 음수가 될 수 없습니다.");
    }
}
