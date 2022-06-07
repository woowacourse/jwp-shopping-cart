package woowacourse.product.domain;

import java.util.Objects;

import woowacourse.product.exception.InvalidStockException;
import woowacourse.product.exception.InvalidProductException;

public class Stock {

    private final int value;

    public Stock(final int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(final int value) {
        if (value <= 0) {
            throw new InvalidStockException("재고는 0 이하의 수가 입력될 수 없습니다.");
        }
    }

    public void checkAvailableForPurchase(final int quantity) {
        if (value < quantity) {
            throw new InvalidProductException("남아있는 재고보다 많은 수량을 카트에 담을 수 없습니다.");
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
        final Stock stock = (Stock)o;
        return value == stock.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
