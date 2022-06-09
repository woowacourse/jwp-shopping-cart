package woowacourse.shoppingcart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AmountTest {

    @DisplayName("개수는 0 미만일 수 없다.")
    @Test
    void validate() {
        Assertions.assertThatThrownBy(() -> new Amount(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
