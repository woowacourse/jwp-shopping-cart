package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.exception.badRequest.InvalidPageException;

class PageTest {

    @DisplayName("요청 첫 페이지가 0보다 작을 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -25, -50, -75, -100})
    void pageWithInValidBeginShouldFail(int begin) {
        int size = 10;
        assertThatThrownBy(() -> Page.of(begin, size))
                .isInstanceOf(InvalidPageException.class);
    }

    @DisplayName("사이즈가 1보다 작을 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50, -100})
    void pageWithInValidSizeShouldFail(int size) {
        int begin = 1;
        assertThatThrownBy(() -> Page.of(begin, size))
                .isInstanceOf(InvalidPageException.class);
    }
}
