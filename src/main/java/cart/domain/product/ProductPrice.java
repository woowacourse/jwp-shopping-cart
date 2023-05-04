package cart.domain.product;

import java.util.Objects;

public class ProductPrice {
    private static final int MAX_RANGE = 100_000_000;

    private final int value;

    public ProductPrice(final int price) {
        validatePrice(price);
        this.value = price;
    }

    private void validatePrice(final int price) {
        if (price > MAX_RANGE) {
            throw new IllegalArgumentException(MAX_RANGE + "원 이하의 가격을 입력해 주세요");
        }
    }

    public int getValue() {
        return value;
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
