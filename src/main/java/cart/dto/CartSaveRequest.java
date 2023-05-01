package cart.dto;

public class CartSaveRequest {

    private final Long userId;
    private final Long productId;
    private final Integer count;

    public CartSaveRequest(final Long userId, final Long productId, final Integer count) {
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }
}
