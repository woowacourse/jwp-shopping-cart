package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {


    @Test
    @DisplayName("상품 이름의 길이가 255자를 넘어가면 예외를 반환한다.")
    void invalidNameLength() {
        //given

        //when

        //then
        assertThatThrownBy(
                () -> new Name("a".repeat(256))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 이름 길이는 255자를 초과할 수 없습니다.");
    }
}
