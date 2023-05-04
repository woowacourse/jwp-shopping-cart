package cart.dto.cart;

public class CartRequest {

    private final Long productId;

    public CartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
