package cart.domain;

import cart.ErrorCode;
import cart.exception.BusinessIllegalArgumentException;

import java.util.Objects;

public class Price {
    private final Long price;

    private Price(Long price) {
        this.price = price;
        validate(price);
    }

    public static Price from(long price) {
        return new Price(price);
    }

    public Long getPrice() {
        return price;
    }

    private void validate(Long price) {
        if (price < 0) {
            throw new BusinessIllegalArgumentException(ErrorCode.NOT_VALID_PRICE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
