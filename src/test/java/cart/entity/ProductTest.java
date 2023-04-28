package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @DisplayName("상품 이름은 1 ~ 20 글자가 아니면 예외가 발생한다")
    @ValueSource(strings = {"", " ", "abcdefghijklmnopqrsuv"})
    @ParameterizedTest
    void invalidName(String name) {
        assertThatThrownBy(() -> new Product(name, null, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름은 1 ~ 20 길이여야 합니다.");
    }

    @DisplayName("상품 이름은 1 ~ 20 글자로 생성할 수 있다")
    @ValueSource(strings = {"a", "abcdefghijklmnopqrsu"})
    @ParameterizedTest
    void validName(String name) {
        assertDoesNotThrow(() -> new Product(name, null, 1000));
    }

    @DisplayName("상품 가격은 0원 이상 1억원 이하가 아니면 ")
    @ValueSource(ints = {0, 100000000})
    @ParameterizedTest
    void validPriceRange(int price) {
        assertDoesNotThrow(() -> new Product("salmon", null, price));
    }

    @DisplayName("상품 가격은 0원 이상 1억원 이하이다")
    @ValueSource(ints = {-1, 100000001})
    @ParameterizedTest
    void invalidPriceRange(int price) {
        assertThatThrownBy(() -> new Product("ocean", null, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 가격은 0원 이상 1억원 이하여야 합니다.");
    }

    @DisplayName("상품 가격은 10원 단위여야 한다")
    @ValueSource(ints = {10, 100, 1000, 1010})
    @ParameterizedTest
    void validUnitOfPrice(int price) {
        assertDoesNotThrow(() -> new Product("salmon", null, price));
    }

    @DisplayName("상품 가격은 0원 이상 1억원 이하이다")
    @ValueSource(ints = {1, 101, 1001, 10001})
    @ParameterizedTest
    void invalidUnitOfPrice(int price) {
        assertThatThrownBy(() -> new Product("ocean", null, price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 가격은 10원 단위여야 합니다.");
    }

}
