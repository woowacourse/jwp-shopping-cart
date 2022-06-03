package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidLengthException;

public class Address {

    private static final int MAX_ADDRESS_LENGTH = 255;

    private final String value;

    public Address(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() >= MAX_ADDRESS_LENGTH) {
            throw InvalidLengthException.fromName("주소");
        }
    }

    public String getValue() {
        return value;
    }
}
