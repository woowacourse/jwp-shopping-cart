package cart.entity.product;

import cart.exception.common.UnderZeroException;

public class Price {

    private final int price;

    public Price(final int price) {
        validatePriceValue(price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    private void validatePriceValue(int price) {
        if (price < 0) {
            throw new UnderZeroException();
        }
    }
}
