package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ImageUrlTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 이미지_경로는_공백일_수_없다(final String imageUrl) {
        assertThatThrownBy(() -> new ImageUrl(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 경로는 공백일 수 없습니다.");
    }
}
