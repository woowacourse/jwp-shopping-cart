package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageUrlTest {

    @DisplayName("이미지 url가 http로 시작하지 않으면 예외를 던진다.")
    @Test
    void checkFormat() {
        assertThatThrownBy(() -> new ImageUrl("abcdefg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이미지 url 형식이 올바르지 않습니다. (형식: http로 시작)");
    }

    @DisplayName("이미지 url 길이가 1024 길면 예외를 던진다.")
    @Test
    void checkLength() {
        String value = "a";
        assertThatThrownBy(() -> new ImageUrl("http" + value.repeat(1024)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이미지 url 길이가 올바르지 않습니다. (길이: 1024자 이내)");
    }

}
