package cart.dto;

public class CartResponse {

    private final Long cartId;
    private final String email;
    private final Long productId;

    public CartResponse(final Long cartId, final String email, final Long productId) {
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
