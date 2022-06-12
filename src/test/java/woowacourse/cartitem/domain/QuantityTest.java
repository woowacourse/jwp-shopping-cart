package woowacourse.cartitem.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.cartitem.exception.InvalidQuantityException;

public class QuantityTest {

    @DisplayName("정상적인 값이 입력되면 예외를 반환하지 않는다.")
    @Test
    void createQuantity() {
        assertDoesNotThrow(() -> new Quantity(1));
    }

    @DisplayName("비정상적인 값이 입력되면 예외를 반환한다.")
    @Test
    void createInvalidQuantity() {
        assertThatThrownBy(() -> new Quantity(-1))
            .isInstanceOf(InvalidQuantityException.class)
            .hasMessage("수량에는 음수가 입력될 수 없습니다.");
    }
}
