package cart.entity;

public final class CartEntity {

    private final Long customerId;
    private final Long productId;

    public CartEntity(final Long customerId, final Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
