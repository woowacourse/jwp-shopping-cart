package cart.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImageUrlTest {
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @NullAndEmptySource
    void 이미지_URL_Null_또는_empty_입력_시_예외_처리(String imageUrl) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ImageUrl(imageUrl))
                .withMessage("[ERROR] 이미지 URL을 입력해주세요.");
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"http://abel.com", "https://abel.com", "http://www.abel.com", "https://www.abel.com", "www.abel.com", "abel.com"})
    void 이미지_URL_정상_입력(String imageUrl) {
        assertThatNoException()
                .isThrownBy(() -> new ImageUrl(imageUrl));
    }
    
    @ParameterizedTest(name = "{displayName} : imageUrl = {0}")
    @ValueSource(strings = {"httpa://abel.com", "htt://abel.com", "https:/www.abel.com", "abel"})
    void 이미지_URL_형식이_올바르지_않을_시_예외_처리(String imageUrl) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ImageUrl(imageUrl))
                .withMessage("[ERROR] 이미지 URL의 형식이 올바르지 않습니다.");
    }
}
