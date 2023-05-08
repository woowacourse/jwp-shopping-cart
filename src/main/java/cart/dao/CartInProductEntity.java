package cart.dao;

public class CartInProductEntity {

    private final Long cartId;
    private final Long productId;

    public CartInProductEntity(final Long cartId, final Long productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
