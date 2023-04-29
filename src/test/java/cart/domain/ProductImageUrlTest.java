package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductImageUrlTest {

    @Test
    @DisplayName("이미지 주소의 길이가 2048 초과일 경우 예외 발생")
    void imageUrlLengthMoreThanMax() {
        String imageUrl = "a".repeat(2049);
        assertThatThrownBy(() -> new ProductImageUrl(imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductImageUrl.IMAGE_URL_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이미지 주소의 길이가 정상일 경우")
    void imageUrlSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new ProductImageUrl(null)),
                () -> assertDoesNotThrow(() -> new ProductImageUrl("")),
                () -> assertDoesNotThrow(() -> new ProductImageUrl("a".repeat(2048)))
        );
    }
}
