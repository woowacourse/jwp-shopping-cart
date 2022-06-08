package woowacourse.shoppingcart.domain.product;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidStockException;

public class Stock {
    private final int value;

    public Stock(int value) {
        validateStock(value);
        this.value = value;
    }

    private void validateStock(int value) {
        if (value <= 0) {
            throw new InvalidStockException();
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
        Stock stock = (Stock) o;
        return value == stock.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "value=" + value +
                '}';
    }
}
