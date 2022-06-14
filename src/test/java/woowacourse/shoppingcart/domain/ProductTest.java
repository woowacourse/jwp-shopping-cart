package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("상품명에 null이 들어온 경우")
    void validateProductNameNull() {
        assertThatThrownBy(() -> new Product(null, 100, "http://example.com/chicken.jpg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지는 null 값이 올 수 없습니다.");
    }

    @Test
    @DisplayName("상품 이미지에 null이 들어온 경우")
    void validateProductImageNull() {
        assertThatThrownBy(() -> new Product("피자", 100, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지는 null 값이 올 수 없습니다.");
    }

    @ParameterizedTest
    @DisplayName("상품명에 null이 들어온 경우")
    @ValueSource(strings = {" ", ""})
    void validateProductNameBlank(String name) {
        assertThatThrownBy(() -> new Product(name, 100, "http://example.com/chicken.jpg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 입력해주세요.");
    }

    @ParameterizedTest
    @DisplayName("상품 이미지에 null이 들어온 경우")
    @ValueSource (strings = {" ", ""})
    void validateProductImageBlank(String image) {
        assertThatThrownBy(() -> new Product("피자", 100, image))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 입력해주세요.");
    }

    @Test
    @DisplayName("수량에 null이 들어온 경우")
    void validateQuantityNull() {
        assertThatThrownBy(() -> new CartItem(1L, 1L, new Product("피자", 20000, "http://example.com/chicken.jpg"),
                null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품과 수량에는 null이 들어올 수 없습니다.");
    }

    @ParameterizedTest
    @DisplayName("가격에 0, 음수가 들어온 경우")
    @CsvSource("0, -1")
    void validateQuantityNegative(int price) {
        assertThatThrownBy(() -> new Product("피자", price, "http://example.com/chicken.jpg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 양수여야 합니다.");
    }
}
