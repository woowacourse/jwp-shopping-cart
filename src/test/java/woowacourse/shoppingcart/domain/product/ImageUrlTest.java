package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.attribute.InvalidFormException;

class ImageUrlTest {

    @DisplayName("이미지 URL을 생성한다.")
    @Test
    void createImageUrl() {
        // given & when
        String value = "http://example.com/image.png";
        // then
        assertThatCode(() -> new ImageUrl(value))
            .doesNotThrowAnyException();
    }

    @DisplayName("이미지 URL을 생성할 때 http로 시작하지 않으면 예외를 던진다.")
    @Test
    void throwsExceptionWithNotStartingHttp() {
        // given & when
        String value = "example.com/image.png";
        // then
        assertThatExceptionOfType(InvalidFormException.class)
            .isThrownBy(() -> new ImageUrl(value));
    }
}