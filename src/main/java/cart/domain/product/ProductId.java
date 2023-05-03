package cart.domain.product;

import java.util.Objects;

public class ProductId {
    private static final ProductId NULL_PRODUCT_ID = new NullProductId();

    private final Long id;

    private ProductId(Long id) {
        this.id = id;
    }

    public static ProductId from(Long id) {
        return new ProductId(id);
    }

    public static ProductId getEmptyId() {
        return NULL_PRODUCT_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductId productId = (ProductId) o;
        return Objects.equals(getId(), productId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    private static class NullProductId extends ProductId {
        private static final String NULL_VALUE_EXCEPTION_MESSAGE = "Id 값이 존재하지 않습니다.";

        public NullProductId() {
            super(Long.MIN_VALUE);
        }

        @Override
        public Long getId() {
            throw new UnsupportedOperationException(NULL_VALUE_EXCEPTION_MESSAGE);
        }
    }
}
