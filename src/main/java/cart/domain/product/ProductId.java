package cart.domain.product;

import java.util.Objects;

public class ProductId {
    private final long id;

    private ProductId(final long id) {
        this.id = id;
    }

    public static ProductId from(final long id) {
        return new ProductId(id);
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductId productId = (ProductId) o;
        return id == productId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
