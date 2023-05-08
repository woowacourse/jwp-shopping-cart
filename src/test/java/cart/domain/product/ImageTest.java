package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ImageTest {

    @Test
    void 이미지url은_null_일수없습니다() {
        assertThatThrownBy(() -> new Image(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 null일 수 없습니다.");
    }

    @Test
    void 이미지url은_2097152자_이하여야_합니다() {
        final String input = "a".repeat(2_097_153);

        assertThatThrownBy(() -> new Image(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 2097152자 이하만 가능합니다.");
    }

    @Test
    void 이미지url은_공백일수없습니다() {
        assertThatThrownBy(() -> new Image(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 url은 공백일 수 없습니다.");
    }

}
