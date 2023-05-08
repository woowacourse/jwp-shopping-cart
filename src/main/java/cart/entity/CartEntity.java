package cart.entity;

public class CartEntity {
    
    public final Long id;
    public final Long userId;
    public final Long productId;

    public CartEntity(final Long id, final Long userId, final Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }
}
