package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("ImageUrl 은(는)")
class ImageUrlTest {


    @ParameterizedTest(name = "이미지 주소가 올바르지 않다면 오류이다. ex = [{0}]")
    @ValueSource(strings = {
            "https:/dqw",
            "https://"
    })
    void 이미지_주소가_올바르지_않다면_오류(final String wrongUrl) {
        // when & then
        assertThatThrownBy(() ->
                ImageUrl.imageUrl(wrongUrl)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미지_주소가_올바르면_생성된다() {
        // when & then
        assertDoesNotThrow(
                () -> ImageUrl.imageUrl("https://woowa.chat")
        );
    }
}
