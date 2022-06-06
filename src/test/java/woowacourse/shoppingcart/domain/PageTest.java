package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.exception.InvalidPageException;

class PageTest {

    @DisplayName("페이지가 정상적으로 생성된다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3, 4, 5, 10})
    void of(final int number) {
        // given
        final int size = 10;

        // when, then
        assertThat(Page.of(number, size)).isNotNull();
    }

    @DisplayName("정상적인 페이지 번호가 아닌 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-10, -5, -1})
    void of_withInvalidPageNumber_throwsException(final int number) {
        // given
        final int size = 10;

        // when, then
        assertThatThrownBy(() -> Page.of(number, size))
                .isInstanceOf(InvalidPageException.class);
    }

    @DisplayName("정상적인 페이지 크기가 아닌 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-10, -5, -1, 0})
    void of_withInvalidPageSize_throwsException(final int size) {
        // given
        final int number = 10;

        // when, then
        assertThatThrownBy(() -> Page.of(number, size))
                .isInstanceOf(InvalidPageException.class);
    }
}
