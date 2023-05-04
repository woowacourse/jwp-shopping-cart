package cart.dao.cart;

public class CartEntity {
    private final Long cartId;
    private final Long productId;
    private final Long userId;

    public CartEntity(Long cartId, Long productId, Long userId) {
        this.cartId = cartId;
        this.productId = productId;
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }
}
