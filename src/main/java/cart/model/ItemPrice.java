package cart.model;

import cart.exception.ErrorStatus;
import cart.exception.ItemException;
import java.math.BigInteger;

public class ItemPrice {

    private static final BigInteger MIN_PRICE = BigInteger.TEN;
    private static final BigInteger MAX_PRICE = BigInteger.valueOf(100_000_000);

    private final BigInteger price;

    public ItemPrice(BigInteger price) {
        validatePrice(price);

        this.price = price;
    }

    private void validatePrice(BigInteger price) {
        if (MIN_PRICE.compareTo(price) > 0 || MAX_PRICE.compareTo(price) < 0) {
            throw new ItemException(ErrorStatus.PRICE_RANGE_ERROR);
        }
    }

    BigInteger getPrice() {
        return price;
    }
}
