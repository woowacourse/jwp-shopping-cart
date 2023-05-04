package cart.dto;

public class CartSaveRequest {

    private Long productId;

    public CartSaveRequest() {
    }

    public CartSaveRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
