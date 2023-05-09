package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.net.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ImageUrlTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("이미지 URL에 빈 값이 들어오면 예외가 발생해야 한다.")
    void create_blankUrl(String imageUrl) {
        /// expect
        assertThatThrownBy(() -> ImageUrl.from(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("URL은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://image.com/image.exe", "https://image.com/image.glen"})
    @DisplayName("이미지의 포맷이 JPG 또는 PNG가 아니면 예외가 발생한다.")
    void create_invalidImageFormat(String imageUrl) {
        // expect
        assertThatThrownBy(() -> ImageUrl.from(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 확장자는 JPG 또는 PNG만 가능합니다.");
    }
    
    @Test
    @DisplayName("이미지 URL의 길이가 100자리를 초과하면 예외가 발생해야 한다.")
    void create_overThan100Characters() {
        // given
        String imageUrl = "https://img.com/" + "123456789".repeat(9) + ".png"; // 101

        // expect
        assertThatThrownBy(() -> ImageUrl.from(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("URL의 길이는 100자리 이하여야 합니다.");
    }

    @Test
    @DisplayName("URL 형식이 아니면 예외가 발생해야 한다.")
    void create_invalidUrl() {
        // expect
        assertThatThrownBy(() -> ImageUrl.from("glen"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 URL 형식이 아닙니다.");
    }

    @Test
    @DisplayName("이미지 URL이 정상적으로 생성되어야 한다.")
    void create_success() throws Exception {
        // given
        ImageUrl imageUrl = ImageUrl.from("https://image.com/image.png");

        // then
        assertThat(imageUrl.getImageUrl())
                .isEqualTo(new URL("https://image.com/image.png"));
    }
}
