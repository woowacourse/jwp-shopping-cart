package cart.persistence.entity;

public class Cart {

    private final Long cartId;
    private final long memberId;
    private final long productId;

    public Cart(final Long cartId, final long memberId, final long productId) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Cart(final long memberId, final long productId) {
        this(null, memberId, productId);
    }

    public Long getCartId() {
        return cartId;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
