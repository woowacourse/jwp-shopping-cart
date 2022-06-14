package woowacourse.shoppingcart.cart.domain;

import woowacourse.shoppingcart.cart.support.exception.CartException;
import woowacourse.shoppingcart.cart.support.exception.CartExceptionCode;

public class Quantity {

    private final long value;

    public Quantity(final long value) {
        validateQuantityPositive(value);
        this.value = value;
    }

    private void validateQuantityPositive(final long value) {
        if (value <= 0) {
            throw new CartException(CartExceptionCode.INVALID_FORMAT_QUANTITY);
        }
    }

    public long value() {
        return value;
    }
}
