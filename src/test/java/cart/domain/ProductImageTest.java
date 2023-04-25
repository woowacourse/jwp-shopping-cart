package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductImageTest {

    @Test
    void 이미지url은_null_일수없습니다() {
        assertThatThrownBy(() -> new ProductImage(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 null일 수 없습니다.");
    }

    @Test
    void 이미지url은_2048자_이하여야_합니다() {
        assertThatThrownBy(() -> new ProductImage("a".repeat(2049)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 2048자 이하만 가능합니다.");
    }

    @Test
    void 이미지url은_공백일수없습니다() {
        assertThatThrownBy(() -> new ProductImage(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 공백일 수 없습니다.");
    }

}
