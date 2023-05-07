package cart.business.domain.product;

import java.util.Objects;

public class ProductId {

    private final Integer id;

    public ProductId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return id.equals(productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
