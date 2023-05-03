package cart.entiy;

public class CartEntity {

    private final Long cartId;
    private final String email;
    private final Long productId;

    public CartEntity(final String email, final Long productId) {
        this(null, email, productId);
    }

    public CartEntity(final Long cartId, final String email, final Long productId) {
        this.cartId = cartId;
        this.email = email;
        this.productId = productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public String getEmail() {
        return email;
    }

    public Long getProductId() {
        return productId;
    }
}
