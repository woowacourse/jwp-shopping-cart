package woowacourse.shoppingcart.customer.domain;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

public class Nickname {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 10;

    private final String value;

    public Nickname(final String value) {
        validateNickname(value);
        this.value = value;
    }

    private void validateNickname(final String value) {
        if (value.isBlank() || isLengthOutOfSize(value)) {
            throw new CustomerException(CustomerExceptionCode.INVALID_FORMAT_NICKNAME);
        }
    }

    private boolean isLengthOutOfSize(final String value) {
        final int length = value.length();
        return (length < MIN_LENGTH) || (length > MAX_LENGTH);
    }

    public String value() {
        return value;
    }
}
