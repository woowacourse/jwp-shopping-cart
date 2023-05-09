package cart.domain.product;

import java.util.Objects;

public class ProductPrice {

    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 10_000_000;
    public static final String PRICE_ERROR_MESSAGE = "상품의 가격은 " + MIN_PRICE + "원 이상 " + MAX_PRICE + "원 이하여야합니다.";

    private final int price;

    public ProductPrice(final int price) {
        validate(price);
        this.price = price;
    }

    private void validate(final int price) {
        if (price < MIN_PRICE || MAX_PRICE < price) {
            throw new IllegalArgumentException(PRICE_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return price == that.price;
    }

    public int getValue() {
        return price;
    }
}
