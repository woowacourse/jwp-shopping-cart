package cart.dao.entity;

public class CartEntity {

    private final Long id;
    private final Long userId;
    private final Long productId;

    public CartEntity(final Long id, final Long userId, final Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public CartEntity(final Long userId, final Long productId) {
        this(null, userId, productId);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
