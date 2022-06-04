package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.attribute.NegativeNumberException;

class PriceTest {

    @DisplayName("금액을 생성한다.")
    @Test
    void createPrice() {
        // given & when
        int amount = 1000;
        // then
        assertThatCode(() -> new Price(amount)).doesNotThrowAnyException();
    }

    @DisplayName("음수로 금액을 생성하면 예외를 던진다.")
    @Test
    void throwsExceptionWithNegativeAmount() {
        // given & when
        int amount = -1;
        // then
        assertThatExceptionOfType(NegativeNumberException.class)
            .isThrownBy(() -> new Price(amount));
    }
}