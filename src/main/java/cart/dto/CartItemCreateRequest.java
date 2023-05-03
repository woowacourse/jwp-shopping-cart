package cart.dto;

public class CartItemCreateRequest {
    private Long productId;

    public CartItemCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
