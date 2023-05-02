package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.domain.product.Product;
import org.junit.jupiter.api.Test;


@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductTest {

    @Test
    void 이름은_null_일수없습니다() {
        assertThatThrownBy(() -> createProductWithName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 null일 수 없습니다.");
    }

    @Test
    void 이름은_255자_이하여야_합니다() {
        final String input = "a".repeat(256);

        assertThatThrownBy(() -> createProductWithName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 255자 이하만 가능합니다.");
    }

    @Test
    void 이름은_공백일수없습니다() {
        assertThatThrownBy(() -> createProductWithName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }

    private Product createProductWithName(final String name) {
        return new Product(name, "url", 1);
    }
}
