package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

public class ProductTest {

    @DisplayName("이름이 비었다면 예외 발생")
    @Test
    void blankProductName_throwException() {
        assertThatThrownBy(() -> Product.builder()
                .productName(" ")
                .price(1_000)
                .stock(100)
                .imageUrl("coffee.png")
                .build())
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 이름은 비워둘 수 없습니다.");
    }
    
    @DisplayName("이름이 64자 이상이라면 예외 발생")
    @Test
    void productName_overLengthLimit_throwException() {
        assertThatThrownBy(() -> Product.builder()
                .productName("01234567890123456789012345678901234567890123456789012345678901234")
                .price(1_000)
                .stock(100)
                .imageUrl("coffee.png")
                .build())
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품 이름은 64자까지 가능합니다.");
    }

    @DisplayName("금액이 0원 또는 이하라면 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {0, -100})
    void zeroOrNegativePrice_throwException(int price) {
        assertThatThrownBy(() -> Product.builder()
                .productName("coffee")
                .price(price)
                .stock(100)
                .imageUrl("coffee.png")
                .build())
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("가격은 최소 1원이어야 합니다.");
    }

    @DisplayName("재고가 음수라면 예외 발생")
    @Test
    void stockUnderZero_throwException() {
        assertThatThrownBy(() -> Product.builder()
                .productName("coffee")
                .price(3_000)
                .stock(-1)
                .imageUrl("coffee.png")
                .build())
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("재고는 음수가 될 수 없습니다.");
    }
}
