package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderDetailTest {

    @Test
    @DisplayName("제품 Id에 null이 들어온 경우")
    void validateProductIdNull() {
        assertThatThrownBy(() -> new OrderDetail(null, 1000, "과자", "http://example.com/chicken.jpg", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모든 값이 입력되지 않았습니다.");
    }

    @Test
    @DisplayName("제품 이름에 null이 들어온 경우")
    void validateProductNameNull() {
        assertThatThrownBy(() -> new OrderDetail(1L, 1000, null, "http://example.com/chicken.jpg", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모든 값이 입력되지 않았습니다.");
    }

    @Test
    @DisplayName("상품 이미지에 null이 들어온 경우")
    void validateProductImageNull() {
        assertThatThrownBy(() -> new OrderDetail(1L, 1000, "과자", null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모든 값이 입력되지 않았습니다.");
    }

    @ParameterizedTest
    @DisplayName("제품 이름에 빈 값이 들어온 경우")
    @ValueSource (strings = {" ", ""})
    void validateProductNameBlank(String name) {
        assertThatThrownBy(() -> new OrderDetail(1L, 1000, name, "http://example.com/chicken.jpg", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 첨부해주세요.");
    }

    @ParameterizedTest
    @DisplayName("제품 이미지에 빈 값이 들어온 경우")
    @ValueSource (strings = {" ", ""})
    void validateProductImageBlank(String image) {
        assertThatThrownBy(() -> new OrderDetail(1L, 1000, "과자", image, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명과 이미지를 첨부해주세요.");
    }

    @ParameterizedTest
    @DisplayName("수량에 0, 음수가 들어온 경우")
    @CsvSource("0, -1")
    void validateQuantityNegative(int quantity) {
        assertThatThrownBy(() -> new OrderDetail(1L, 1000, "과자", "http://example.com/chicken.jpg", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가격과 수량은 양수이어야 합니다.");
    }

    @ParameterizedTest
    @DisplayName("가격에 0, 음수가 들어온 경우")
    @CsvSource("0, -1")
    void validatePriceyNegative(int price) {
        assertThatThrownBy(() -> new OrderDetail(1L, price, "과자", "http://example.com/chicken.jpg",1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가격과 수량은 양수이어야 합니다.");
    }
}
