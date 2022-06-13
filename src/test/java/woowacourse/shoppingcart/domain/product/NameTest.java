package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("상품명이 null이면 예외를 던진다.")
    @Test
    void checkNull() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("상품명은 필수 입력 사항입니다.");
    }

    @DisplayName("상품명이 null이면 예외를 던진다.")
    @Test
    void checkLength() {
        String value = "a";
        assertThatThrownBy(() -> new Name(value.repeat(101)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명 길이가 올바르지 않습니다. (길이: 100자 이내)");
    }
}
