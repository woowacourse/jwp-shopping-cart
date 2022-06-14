package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidFormException;

public class Price {

    private static final int MIN_PRODUCT_PRICE = 0;

    private final int value;

    public Price(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_PRODUCT_PRICE) {
            throw InvalidFormException.fromName("상품 가격");
        }
    }

    public int getValue() {
        return value;
    }
}
