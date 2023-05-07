package cart.product.domain;

import java.util.Objects;

public class ProductId {

    private final Long id;

    private ProductId(final Long id) {
        this.id = id;
    }

    public static ProductId from(final Long id) {
        return new ProductId(id);
    }

    public static ProductId getEmptyInstance() {
        return new ProductId(null);
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
        return Objects.equals(getId(), productId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }
}
