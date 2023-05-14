package cart.dto;

public class CartItemRequest {

    private Long productId;

    public CartItemRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
