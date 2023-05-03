package cart.domain.product;

import java.util.Objects;

public class ProductPrice {
    private static final int MIN_PRICE_RANGE = 0;
    private static final int MAX_PRICE_RANGE = 100_000_000;
    private final int value;

    public ProductPrice(final int price) {
        validatePrice(price);
        this.value = price;
    }

    public int getValue() {
        return value;
    }

    private void validatePrice(final int price) {
        if (price < MIN_PRICE_RANGE || price > MAX_PRICE_RANGE) {
            throw new IllegalArgumentException("0원 이상 1억 이하의 가격을 입력해주세요.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice productPrice = (ProductPrice) o;
        return value == productPrice.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
