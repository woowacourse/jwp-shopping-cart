package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

public class Price {
    private final int value;

    private Price(int value) {
        validate(value);
        this.value = value;
    }

    public static Price of(int value) {
        return new Price(value);
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return getValue() == price.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
