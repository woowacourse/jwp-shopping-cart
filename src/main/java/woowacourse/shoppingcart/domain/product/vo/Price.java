package woowacourse.shoppingcart.domain.product.vo;

import java.util.Objects;

public class Price {

    private static final int MIN_VALUE = 1;

    private final int value;

    public Price(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(
                    String.format("가격은 %d원 이상이어야 합니다. 입력값: %d", MIN_VALUE, value));
        }
    }

    public int multiple(int number) {
        return value * number;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
