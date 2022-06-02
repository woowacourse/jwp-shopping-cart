package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AgeTest {

    @DisplayName("나이가 음수이면 예외가 발생한다")
    @Test
    void construct_negative() {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new Age(-1))
                .withMessageContaining("0살 이상");
    }
}