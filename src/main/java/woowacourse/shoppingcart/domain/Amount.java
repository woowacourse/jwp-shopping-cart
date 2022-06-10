package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Amount {

    private static final int MIN_VALUE = 0;

    private final int value;

    public Amount(int value) {
        validateAmount(value);

        this.value = value;
    }

    private void validateAmount(int value) {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException("개수는 0 미만일 수 없습니다.");
        }
    }

    public boolean isMoreThan(Amount other) {
        return this.value >= other.value;
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
        Amount amount = (Amount) o;
        return value == amount.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
