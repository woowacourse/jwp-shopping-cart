package cart.domain.product;

import java.util.Objects;

public class ProductId {
    private final long value;

    public ProductId(final long id) {
        this.value = id;
    }

    public long getValue() {
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
        final ProductId productId1 = (ProductId) o;
        return value == productId1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
