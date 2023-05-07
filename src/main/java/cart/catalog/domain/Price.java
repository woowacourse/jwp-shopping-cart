package cart.catalog.domain;

import cart.common.exceptions.InvalidInputException;

public class Price {

    public static final String NEGATIVE_PRICE_ERROR = "상품의 가격은 0보다 작을 수 없습니다.";

    private final int value;

    public Price(final int value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < 0) {
            throw new InvalidInputException(NEGATIVE_PRICE_ERROR);
        }
    }

    public int getValue() {
        return this.value;
    }
}
