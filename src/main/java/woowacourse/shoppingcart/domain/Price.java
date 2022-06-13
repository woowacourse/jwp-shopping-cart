package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInformationException;

import java.util.Objects;

public class Price {
    private static final String NOT_ZERO_OR_NEGATIVE = "[ERROR] 가격은 자연수이어야 합니다.";

    private final int price;

    public Price(int price) {
        validateZeroOrNegative(price);
        this.price = price;
    }

    private void validateZeroOrNegative(int price) {
        if (price <= 0) {
            throw new InvalidInformationException(NOT_ZERO_OR_NEGATIVE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    public int getPrice() {
        return price;
    }
}
