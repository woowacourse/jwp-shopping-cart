package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.badrequest.InvalidPriceException;

public class PriceTest {

    @DisplayName("상품의 가격이 0원 미만이면 예외를 발생시킨다.")
    @Test
    void NegativeValuePriceException() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(InvalidPriceException.class);
    }
}
