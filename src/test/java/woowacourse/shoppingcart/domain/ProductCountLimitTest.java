package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductCountLimitTest {

    @DisplayName("한 페이지 보여줄 상품의 개수가 1보다 작다면 예외를 발생시킨다.")
    @Test
    void create_NotPositiveValue() {
        assertThatThrownBy(() -> new ProductCountLimit(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("한 페이지에 보여줄 상품의 개수는 양수여야합니다.");
    }
}
