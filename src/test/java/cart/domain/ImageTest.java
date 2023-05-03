package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ImageTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void 이미지_url이_비어있으면_예외가_발생한다(String url) {
        Assertions.assertThatThrownBy(() -> new Image(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 URL은 비어있을 수 없습니다.");
    }
}
