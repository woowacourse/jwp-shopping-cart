package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixtures.치킨;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

class CartTest {

    @DisplayName("물품 개수는 1 이상이어야 한다.")
    @Test
    void getQuantity() {
        assertThatThrownBy(() -> new Cart(1L, 0, 치킨))
                .isInstanceOf(InvalidCartItemException.class);
    }
}
