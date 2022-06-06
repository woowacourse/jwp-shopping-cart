package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    void 나이가_0이하인_경우_예외발생() {
        assertThatThrownBy(() -> new Age(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
