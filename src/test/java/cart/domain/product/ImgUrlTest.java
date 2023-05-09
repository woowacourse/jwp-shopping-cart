package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImgUrlTest {

    @Test
    @DisplayName("url이 빈 칸일 경우 예외가 발생한다.")
    void validateUrlNotBlankTest() {
        // given
        String url = "";

        // then
        assertThatThrownBy(() -> new ImgUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 URL을 입력하세요.");
    }

    @Test
    @DisplayName("url이 url 형식이 아닐 경우 예외가 발생한다.")
    void validateUrlFormatTest() {
        // given
        String url = "invalidUrl";

        // then
        assertThatThrownBy(() -> new ImgUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 URL 입니다.");
    }

    @Test
    @DisplayName("url이 8000자 초과일 경우 예외가 발생한다.")
    void validateUrlLengthTest() {
        // given
        int urlLength = 8001;
        String url = "a".repeat(urlLength - 4) + ".com";

        // then
        assertThatThrownBy(() -> new ImgUrl(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("url은 8000자 이하여야 합니다. (현재 " + urlLength + "자)");
    }
}