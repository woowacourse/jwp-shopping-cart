package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageUrlTest {

    @Test
    @DisplayName("url 길이가 2000자를 넘어가면 예외를 반환한다.")
    void invalidImageUrlLength() {
        //given

        //when

        //then
        assertThatThrownBy(
                () -> new ImageUrl("a".repeat(2001))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("url 길이는 2000자를 초과할 수 없습니다.");
    }

}
