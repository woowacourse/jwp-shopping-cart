package cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    private final BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }

    private void validate() {
        if(price.compareTo(MIN_PRICE) < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상이어야 합니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

}
