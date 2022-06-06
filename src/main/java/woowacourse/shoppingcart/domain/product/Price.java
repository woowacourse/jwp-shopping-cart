package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

public class Price {

    private final int value;

    public Price(int value) {
        checkBoundary(value);
        this.value = value;
    }

    private void checkBoundary(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("상품 금액은 0원 이상이어야 합니다.");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Price price = (Price)o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
