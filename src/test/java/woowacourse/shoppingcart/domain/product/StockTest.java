package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.format.InvalidStockException;

public class StockTest {
    @DisplayName("재고 객체를 생성한다.")
    @Test
    public void construct() {
        // given
        int stock = 3;

        // when & then
        assertThatCode(() -> new Stock(stock)).doesNotThrowAnyException();
    }

    @DisplayName("음수 재고를 입력받으면 예외 발생")
    @Test
    public void constructFailed() {
        // given
        int stock = -1;

        // when & then
        assertThatThrownBy(() -> new Stock(stock)).isInstanceOf(InvalidStockException.class);
    }
}
