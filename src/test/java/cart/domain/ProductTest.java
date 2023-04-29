package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Test
    @DisplayName("상품이 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Product product = new Product("glenfiddich", 10000, "image.png");

        // expect
        assertThat(product.getName())
                .isEqualTo("glenfiddich");
        assertThat(product.getPrice())
                .isEqualTo(10000);
        assertThat(product.getImageUrl())
                .isEqualTo("image.png");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("이름에 빈 값이 들어오면 예외가 발생해야 한다.")
    void create_blankName(String name) {
        // expect
        assertThatThrownBy(() -> new Product(name, 10000, "image.png"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    @DisplayName("가격이 0 이하이면 예외가 발생해야 한다.")
    void create_priceIsZeroOrLess(int price) {
        // given
        assertThatThrownBy(() -> new Product("glenfiddich", price, "image.png"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0 이하 일 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("이미지 URL에 빈 값이 들어오면 예외가 발생해야 한다.")
    void create_blankImageUrl(String imageUrl) {
        /// expect
        assertThatThrownBy(() -> new Product("glenfiddich", 10000, imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이미지 URL은 빈 값이 될 수 없습니다.");
    }
}
