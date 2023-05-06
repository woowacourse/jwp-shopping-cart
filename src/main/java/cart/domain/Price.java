package cart.domain;

import cart.domain.exception.WrongPriceException;
import cart.domain.exception.WrongPriceException.Language;

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
        throw new WrongPriceException(Language.KO);
    }

    public int getValue() {
        return value;
    }
}
