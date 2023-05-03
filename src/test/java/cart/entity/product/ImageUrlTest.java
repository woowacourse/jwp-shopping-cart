package cart.entity.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import cart.exception.common.NullOrBlankException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ImageUrlTest {

    @ParameterizedTest(name = "{displayName}")
    @NullSource
    @ValueSource(strings = {" ", "", "   "})
    @DisplayName("이미지 URL이 {0}이면 에러를 발생시킨다.")
    void validate_name_null_or_blank(String imageUrl) {
        // when + then
        assertThatThrownBy(() -> new ImageUrl(imageUrl))
            .isInstanceOf(NullOrBlankException.class);

    }
}
