package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.invalid.InvalidIdException;

class IdTest {

    @ParameterizedTest
    @ValueSource(longs = {1L, 10L, 22L, 1000L})
    void ID_생성(long value) {
        // when
        Id id = new Id(value);

        // then
        assertThat(id.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(longs = {-10L, -1L, 0L})
    void ID가_1이상의_정수가_아니면_예외_발생(long value) {
        assertThatThrownBy(() -> new Id(value))
                .isInstanceOf(InvalidIdException.class);
    }
}
