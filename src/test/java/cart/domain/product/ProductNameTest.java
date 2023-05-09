package cart.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class ProductNameTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("상품 이름에 빈 값이 들어오면 예외가 발생해야 한다.")
    void create_blankName(String name) {
        // expect
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("상품 이름의 길이가 10자리를 초과하면 예외가 발생해야 한다.")
    void create_overThan20Characters() {
        // expect
        Assertions.assertThatThrownBy(() -> new ProductName("12345678901")) // 11
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름의 길이는 10자리 이하여야 합니다.");
    }

    @Test
    @DisplayName("상품 이름이 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        ProductName productName = new ProductName("글렌피딕");

        // expect
        assertThat(productName.getName())
                .isEqualTo("글렌피딕");
    }
}
