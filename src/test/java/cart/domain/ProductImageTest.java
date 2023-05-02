package cart.domain;

import cart.domain.product.ProductImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductImageTest {

    @Test
    @DisplayName("이미지 주소의 길이가 2048 초과일 경우 예외 발생")
    void imageLengthMoreThanMax() {
        String image = "a".repeat(2049);
        assertThatThrownBy(() -> new ProductImage(image))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductImage.IMAGE_URL_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이미지 주소의 길이가 정상일 경우")
    void imageSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new ProductImage(null)),
                () -> assertDoesNotThrow(() -> new ProductImage("")),
                () -> assertDoesNotThrow(() -> new ProductImage("a".repeat(2048)))
        );
    }
}
