package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.badrequest.InvalidPriceException;

public class Price {

    private final int price;

    public Price(int price) {
        this.price = price;
        validate();
    }

    private void validate() {
        if (price < 0) {
            throw new InvalidPriceException();
        }
    }

    public int value() {
        return price;
    }
}
