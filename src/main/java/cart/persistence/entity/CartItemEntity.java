package cart.persistence.entity;

public class CartItemEntity {

    private final Long cartItemId;
    private final Long cartId;
    private final Long productId;

    public CartItemEntity(Long cartItemId, Long cartId, Long productId) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
    }

    public CartItemEntity(Long cartId, Long productId) {
        this(null, cartId, productId);
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
