package cart.model;

import cart.exception.item.ItemFieldNotValidException;
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
            throw new ItemFieldNotValidException("상품의 금액은 최소 10원, 최대 1억원까지 입력할 수 있습니다.");
        }
    }

    BigInteger getPrice() {
        return price;
    }
}
