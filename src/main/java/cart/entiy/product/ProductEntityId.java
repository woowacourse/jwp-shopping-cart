package cart.entiy.product;

import cart.domain.product.ProductId;
import java.util.Objects;

public class ProductEntityId {

    private final Long value;

    public ProductEntityId(final Long value) {
        this.value = value;
    }

    public static ProductEntityId from(final ProductId productId) {
        return new ProductEntityId(productId.getValue());
    }

    public ProductId toDomain() {
        return new ProductId(value);
    }

    public Long getValue() {
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
        final ProductEntityId that = (ProductEntityId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
