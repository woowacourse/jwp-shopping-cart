package cart.dto;

public class CartSaveRequest {

    private final Long userId;
    private final Long productId;

    public CartSaveRequest(final Long userId, final Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
