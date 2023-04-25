package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductNameTest {

    @Test
    void 이름은_null_일수없습니다() {
        assertThatThrownBy(() -> new ProductName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 null일 수 없습니다.");
    }

    @Test
    void 이름은_255자_이하여야_합니다() {
        assertThatThrownBy(() -> new ProductName("a".repeat(256)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 255자 이하만 가능합니다.");
    }

    @Test
    void 이름은_공백일수없습니다() {
        assertThatThrownBy(() -> new ProductName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }

}
