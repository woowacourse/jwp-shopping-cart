package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("product를 정상적으로 생성한다")
    void create_success() {
        assertThatNoException().isThrownBy(() -> new Product("name", 1000, "testUrl"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123456789012345678901"})
    @DisplayName("상품 이름의 길이가 1자 이상 20자 이하가 아닌 경우 예외가 발생한다.")
    void validateName(String wrongValue) {
        assertThatThrownBy(() -> new Product(wrongValue, 1000, "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 1자 이상, 20자 이하입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1050, 1150})
    @DisplayName("상품 가격이 100원 단위가 아닌경우 예외가 발생한다")
    void validateUnitOfPrice(int price) {
        assertThatThrownBy(() -> new Product("귤", price, "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격 단위는 100원 단위입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 0, -1000})
    @DisplayName("상품의 가격은 1000원 이상이여야한다.")
    void validateMinPrice(int price) {
        assertThatThrownBy(() -> new Product("귤", price, "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 최소 가격은 1000원 이상입니다.");
    }
}

