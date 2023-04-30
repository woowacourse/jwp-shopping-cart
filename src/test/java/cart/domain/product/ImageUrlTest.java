package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ImageUrlTest {

    @ParameterizedTest
    @ValueSource(strings = {"http://", "https://"})
    @DisplayName("상품 이미지를 정상적으로 등록할 수 있다")
    void imageUrlTest(final String input) {
        assertDoesNotThrow(() -> ImageUrl.from(input));
    }

    @DisplayName("형식에 맞지 않는 경로가 들어오면 예외를 발생한다")
    @ValueSource(strings = {"http:", "https:", "http:/", "https:/"})
    @ParameterizedTest
    void throwExceptionWhenInvalidImageUrl(final String input) {
        assertThatThrownBy(() -> ImageUrl.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
