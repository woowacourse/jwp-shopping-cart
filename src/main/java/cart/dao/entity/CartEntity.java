package cart.dao.entity;

public class CartEntity {

    private final Long id;
    private final long productId;
    private final long customerId;

    public CartEntity(final Long id, final long productId, final long customerId) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
    }

    public static class Builder {
        private Long id;
        private long productId;
        private long customerId;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder productId(long productId) {
            this.productId = productId;
            return this;
        }

        public Builder customerId(long customerId) {
            this.customerId = customerId;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(id, productId, customerId);
        }
    }

    public Long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getCustomerId() {
        return customerId;
    }
}
