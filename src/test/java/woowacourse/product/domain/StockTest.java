package woowacourse.product.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.product.exception.InvalidStockException;

public class StockTest {

    @DisplayName("정상적인 값을 입력한 경우 예외가 발생하지 않는다.")
    @Test
    void createPrice() {
        assertDoesNotThrow(() -> new Stock(1));
    }

    @DisplayName("0 이하의 수가 입력된 경우 예외를 발생한다.")
    @Test
    void createInvalidPrice() {
        assertThatThrownBy(() -> new Stock(0))
            .isInstanceOf(InvalidStockException.class)
            .hasMessage("재고는 0 이하의 수가 입력될 수 없습니다.");
    }
}
