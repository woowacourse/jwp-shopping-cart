package cart.domain.product;

import java.util.Objects;

public final class Price {

    private static final int MIN = 0;
    private static final int MAX = 10_000_000;

    private final Integer price;

    public Price(final Integer price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(final Integer price) {
        if (price < MIN || price > MAX) {
            throw new IllegalArgumentException("상품 가격은 " + MIN + " ~ " + MAX + "원까지 가능합니다.");
        }
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Price other = (Price) o;
        return Objects.equals(price, other.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
