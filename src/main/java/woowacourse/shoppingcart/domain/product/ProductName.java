package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidLengthException;

public class ProductName {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 1;

    private final String value;

    public ProductName(String value) {
        value = value.trim();
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (isLengthOutOfRange(value)) {
            throw InvalidLengthException.fromName("상품 이름");
        }
    }

    private boolean isLengthOutOfRange(String value) {
        return value.length() < MIN_NAME_LENGTH || value.length() > MAX_NAME_LENGTH;
    }

    public String getValue() {
        return value;
    }
}
