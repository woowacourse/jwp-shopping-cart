package cart.dto;

public class CartItemRequest {

    private Long productId;

    private CartItemRequest() {
    }
    
    public CartItemRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
