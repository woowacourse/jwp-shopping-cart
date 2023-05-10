package cart.domain;

import cart.domain.exception.WrongPriceException;

public class Price {

    private static final int AMOUNT_LIMIT = 0;

    private final int value;

    public Price(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int price) {
        if (price > AMOUNT_LIMIT) {
            return;
        }
        throw new WrongPriceException();
    }

    public int getValue() {
        return value;
    }
}
