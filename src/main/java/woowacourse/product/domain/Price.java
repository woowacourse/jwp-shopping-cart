package woowacourse.product.domain;

import java.util.Objects;

import woowacourse.product.exception.InvalidPriceException;

public class Price {

    private final int value;

    public Price(final int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(final int value) {
        if (value <= 0) {
            throw new InvalidPriceException("가격에는 0 이하의 수가 입력될 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Price price = (Price)o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
