package cart.entity.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ImageUrlTest {

    @ParameterizedTest(name = "이미지 경로가 {0}일 떄")
    @NullAndEmptySource
    @DisplayName("이미지 경로가 존재하지 않을 경우 예외를 던진다.")
    void imageUrlNotExist(final String value) {
        assertThatThrownBy(() -> new ImageUrl(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
