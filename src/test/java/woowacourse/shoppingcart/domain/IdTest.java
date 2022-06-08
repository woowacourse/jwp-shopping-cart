package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.invalid.InvalidIdException;

class IdTest {

    @Test
    void ID가_1이상의_정수가_아니면_예외_발생() {
        assertThatThrownBy(() -> new Id(0L))
                .isInstanceOf(InvalidIdException.class);
    }
}
