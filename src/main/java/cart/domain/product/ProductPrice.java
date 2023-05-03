package cart.domain.product;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

public class ProductPrice {
    private static final int MAX_RANGE = 100_000_000;

    @PositiveOrZero
    @Max(value = MAX_RANGE, message = MAX_RANGE + "원 이하의 가격을 입력해주세요.")
    private final int value;

    public ProductPrice(final int price) {
        this.value = price;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice productPrice = (ProductPrice) o;
        return value == productPrice.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
