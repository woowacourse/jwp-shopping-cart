package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.format.InvalidPriceException;

public class PriceTest {
    @DisplayName("가격 객체를 생성한다.")
    @Test
    public void construct() {
        // given
        int price = 30;

        // when & then
        assertThatCode(() -> new Price(price)).doesNotThrowAnyException();
    }

    @DisplayName("최소 금액에 나누어 떨어지지 않는 금액을 입력받으면 예외 발생")
    @Test
    public void constructFailed() {
        // given
        int price = 9;

        // when & then
        assertThatThrownBy(() -> new Price(price)).isInstanceOf(InvalidPriceException.class);
    }
}
