package cart.dao;

public class CartInProductEntity {

    private Long id;
    private final Long cartId;
    private final Long productId;

    public CartInProductEntity(final Long id, final Long cartId, final Long productId) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartInProductEntity(final Long cartId, final Long productId) {
        this(null, cartId, productId);
    }

    public Long getId() {
        return id;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
