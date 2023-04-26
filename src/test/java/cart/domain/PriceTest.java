package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PriceTest {

    @Test
    void 가격이_음수면_예외가_발생한다() {
        BigDecimal value = new BigDecimal(-1);

        Assertions.assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 음수 혹은 빈 값이 될 수 없습니다.");
    }

    @Test
    void 가격이_10억을_초과하면_예외가_발생한다() {
        BigDecimal value = new BigDecimal(1_000_000_001);

        Assertions.assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 10억을 초과할 수 없습니다.");
    }

    @Test
    void 가격이_빈_값이면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> new Price(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 음수 혹은 빈 값이 될 수 없습니다.");
    }
}
