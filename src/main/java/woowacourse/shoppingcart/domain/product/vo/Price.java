package woowacourse.shoppingcart.domain.product.vo;

import java.util.Objects;

import woowacourse.shoppingcart.exception.InvalidPriceException;

public class Price {
    private final int price;

    public Price(int value) {
        validatePositive(value);
        this.price = value;
    }

    private void validatePositive(int value) {
        if (value < 0) {
            throw new InvalidPriceException();
        }
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Price))
            return false;
        Price price = (Price)o;
        return getPrice() == price.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }
}
