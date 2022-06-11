package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PageTest {

    @DisplayName("음수의 값으로 Page 객체를 생성하려하면 예외를 발생시킨다.")
    @Test
    void create_NotPositiveValue() {
        assertThatThrownBy(() -> new Page(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("page 값은 양수여야 합니다.");
    }

    @DisplayName("표시할 상품 개수를 받아, 페이지네이션에서 필요한 offset을 반환한다.")
    @ParameterizedTest
    @CsvSource({"1, 10, 0", "2, 7, 7"})
    void calculateOffset(int pageValue, int productCountLimit, int expected) {
        Page page = new Page(pageValue);

        int offset = page.calculateOffset(productCountLimit);

        assertThat(offset).isEqualTo(expected);
    }
}