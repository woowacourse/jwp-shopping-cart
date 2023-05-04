package cart.domain.product;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ImageUrlTest {

    @ParameterizedTest
    @ValueSource(strings = {"http://", "https://"})
    void 상품_이미지를_정상적으로_등록할_수_있다(final String input) {
        assertDoesNotThrow(() -> ImageUrl.from(input));
    }

    @ValueSource(strings = {"http:", "https:", "http:/", "https:/"})
    @ParameterizedTest
    void 형식에_맞지_않는_이미지_경로가_들어오면_예외를_발생한다(final String input) {
        assertThatThrownBy(() -> ImageUrl.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
