package cart.dto.cart;

public class CartCreateRequest {
    private final Long productId;

    public CartCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
