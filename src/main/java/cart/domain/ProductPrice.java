package cart.domain;

import static cart.exception.ErrorCode.PRODUCT_PRICE_RANGE;

import cart.exception.GlobalException;

public class ProductPrice {

    private static final int PRICE_MIN_RANGE = 0, PRICE_MAX_RANGE = 10_000_000;

    private final int price;

    private ProductPrice(final int price) {
        this.price = price;
    }

    public static ProductPrice create(final int price) {
        validatePrice(price);
        return new ProductPrice(price);
    }

    private static void validatePrice(final int price) {
        if (price < PRICE_MIN_RANGE || price > PRICE_MAX_RANGE) {
            throw new GlobalException(PRODUCT_PRICE_RANGE);
        }
    }

    public int getPrice() {
        return price;
    }
}
